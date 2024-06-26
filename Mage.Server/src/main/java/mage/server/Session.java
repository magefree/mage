package mage.server;

import mage.MageException;
import mage.constants.Constants;
import mage.interfaces.callback.ClientCallback;
import mage.interfaces.callback.ClientCallbackMethod;
import mage.players.net.UserData;
import mage.players.net.UserGroup;
import mage.server.game.GamesRoom;
import mage.server.managers.ConfigSettings;
import mage.server.managers.ManagerFactory;
import mage.util.RandomUtil;
import mage.util.ThreadUtils;
import mage.utils.SystemUtil;
import org.apache.log4j.Logger;
import org.jboss.remoting.callback.AsynchInvokerCallbackHandler;
import org.jboss.remoting.callback.Callback;
import org.jboss.remoting.callback.HandleCallbackException;
import org.jboss.remoting.callback.InvokerCallbackHandler;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class Session {

    private static final Logger logger = Logger.getLogger(Session.class);

    private static final Pattern alphabetsPattern = Pattern.compile("[a-zA-Z]");
    private static final Pattern digitsPattern = Pattern.compile("[0-9]");

    public static final String REGISTRATION_DISABLED_MESSAGE = "Registration has been disabled on the server. You can use any name and empty password to login.";

    // Connection and reconnection logic:
    // - server allows only one user intance (user name for search, userId for memory/db store)
    // - if same user connected then all other user's intances must be removed (disconnected)
    // - for auth mode: identify intance by userId (must disconnect all other clients with same user name)
    // - for anon mode: identify intance by unique mark like IP/host (must disconnect all other clients with same user name + IP)
    // - for any mode: optional identify by restore session (must disconnect all other clients with same user name)
    // - TODO: implement client id mark instead host like md5(mac, os, somefolders)
    // - if server can't remove another user intance then restrict to connect (example: anon user connected, but there is another anon with diff IP)
    private static final boolean ANON_IDENTIFY_BY_HOST = true; // for anon mode only: true - kick all other users with same IP; false - keep first connected user

    // async data transfer for all callbacks (transfer of game updates from server to client):
    // - pros:
    //   * SIGNIFICANT performance boost and pings (yep, that's true);
    //   * no freeze with disconnected/slow watchers/players;
    // - cons:
    //   * data re-sync? TODO: need research, possible problem: outdated battlefield, need click on avatar
    //   * what about slow connection or big pings? TODO: need research, possible problem: too many re-sync messages in client logs, outdated battlefield, wrong answers
    //   * what about disconnected players? TODO: need research, possible problem: server can't detect disconnected players too fast
    //   * what about too many requests to freezed players (too much threads)?
    //     TODO: need research, possible problem: if something bad with server connection then it can generates
    //      too many requests to a client and can cause threads overflow/limit (example: watch AI only games?);
    //      visualvm can help with threads monitor
    private static final boolean SUPER_DUPER_BUGGY_AND_FASTEST_ASYNC_CONNECTION = false; // TODO: enable after full research

    private final ManagerFactory managerFactory;
    private final String sessionId;
    private UUID userId;
    private String host;
    private final AtomicInteger messageId = new AtomicInteger(0);
    private final Date timeConnected;
    private boolean isAdmin = false;
    private final AsynchInvokerCallbackHandler callbackHandler;
    private boolean valid = true;

    private final ReentrantLock lock;
    private final ReentrantLock callBackLock;

    public Session(ManagerFactory managerFactory, String sessionId, InvokerCallbackHandler callbackHandler) {
        this.managerFactory = managerFactory;
        this.sessionId = sessionId;
        this.callbackHandler = (AsynchInvokerCallbackHandler) callbackHandler;
        this.isAdmin = false;
        this.timeConnected = new Date();
        this.lock = new ReentrantLock();
        this.callBackLock = new ReentrantLock();
    }

    public String registerUser(String userName, String password, String email) {
        if (!managerFactory.configSettings().isAuthenticationActivated()) {
            String returnMessage = REGISTRATION_DISABLED_MESSAGE;
            sendErrorMessageToClient(returnMessage);
            return returnMessage;
        }
        synchronized (AuthorizedUserRepository.getInstance()) {
            // name
            String returnMessage = validateUserName(userName);
            if (returnMessage != null) {
                sendErrorMessageToClient(returnMessage);
                return returnMessage;
            }

            // auto-generated password
            RandomString randomString = new RandomString(10);
            password = randomString.nextString();
            returnMessage = validatePassword(password, userName);
            if (returnMessage != null) {
                sendErrorMessageToClient("Auto-generated password fail, try again: " + returnMessage);
                return returnMessage;
            }

            // email
            returnMessage = validateEmail(email);
            if (returnMessage != null) {
                sendErrorMessageToClient(returnMessage);
                return returnMessage;
            }

            // create
            AuthorizedUserRepository.getInstance().add(userName, password, email);
            String text = "You are successfully registered as " + userName + '.';
            text += "  Your initial, generated password is: " + password;

            boolean success;
            String subject = "XMage Registration Completed";
            if (!managerFactory.configSettings().getMailUser().isEmpty()) {
                success = managerFactory.mailClient().sendMessage(email, subject, text);
            } else {
                success = managerFactory.mailgunClient().sendMessage(email, subject, text);
            }
            if (success) {
                String ok = "Email with initial password sent to " + email + " for a user " + userName;
                logger.info(ok);
                sendInfoMessageToClient(ok);
            } else if (Main.isTestMode()) {
                String ok = "Email sending failed. Server is in test mode. Your account registered with a password " + password + " for a user " + userName;
                logger.info(ok);
                sendInfoMessageToClient(ok);
            } else {
                String err = "Email sending failed. Try use another email address or service. Or reset password by email " + email + " for a user " + userName;
                logger.error(err);
                sendErrorMessageToClient(err);
                return err;
            }
            return null;
        }
    }

    private String validateUserNameLength(String userName) {
        ConfigSettings config = managerFactory.configSettings();
        if (userName.length() < config.getMinUserNameLength()) {
            return "User name may not be shorter than " + config.getMinUserNameLength() + " characters";
        }
        if (userName.length() > config.getMaxUserNameLength()) {
            return "User name may not be longer than " + config.getMaxUserNameLength() + " characters";
        }
        if (userName.length() <= 3) {
            return "User name is too short (3 characters or fewer)";
        }
        if (userName.length() >= 500) {
            return "User name is too long (500 characters or more)";
        }
        return null;
    }

    private String validateUserName(String userName) {
        if (userName.equals(User.ADMIN_NAME)) {
            return "User name already in use";
        }

        String returnMessage = validateUserNameLength(userName);
        if (returnMessage != null) {
            return returnMessage;
        }

        Pattern invalidUserNamePattern = Pattern.compile(managerFactory.configSettings().getInvalidUserNamePattern(), Pattern.CASE_INSENSITIVE);
        Matcher m = invalidUserNamePattern.matcher(userName);
        if (m.find()) {
            return "User name '" + userName + "' includes not allowed characters: use a-z, A-Z and 0-9";
        }

        AuthorizedUser authorizedUser = AuthorizedUserRepository.getInstance().getByName(userName);
        if (authorizedUser != null) {
            return "User name '" + userName + "' already in use";
        }

        // all fine
        return null;
    }

    private String validatePassword(String password, String userName) {
        ConfigSettings config = managerFactory.configSettings();
        if (password.length() < config.getMinPasswordLength()) {
            return "Password may not be shorter than " + config.getMinPasswordLength() + " characters";
        }
        if (password.length() > config.getMaxPasswordLength()) {
            return "Password may not be longer than " + config.getMaxPasswordLength() + " characters";
        }
        if (password.equals(userName)) {
            return "Password may not be the same as your username";
        }
        Matcher alphabetsMatcher = alphabetsPattern.matcher(password);
        Matcher digitsMatcher = digitsPattern.matcher(password);
        if (!alphabetsMatcher.find() || !digitsMatcher.find()) {
            return "Password has to include at least one alphabet (a-zA-Z) and also at least one digit (0-9)";
        }
        return null;
    }

    private static String validateEmail(String email) {

        if (email == null || email.isEmpty()) {
            return "Email address cannot be blank";
        }
        AuthorizedUser authorizedUser = AuthorizedUserRepository.getInstance().getByEmail(email);
        if (authorizedUser != null) {
            return "Email address '" + email + "' is associated with another user";
        }
        return null;
    }

    public String connectUser(String userName, String password, String restoreSessionId) throws MageException {
        // check username
        String errorMessage = validateUserNameLength(userName);
        if (errorMessage != null) {
            return errorMessage;
        }

        // check auth/anon
        errorMessage = connectUserHandling(userName, password, restoreSessionId);
        return errorMessage;
    }

    public boolean isLocked() {
        return lock.isLocked();
    }

    /**
     * Auth user on server (link current session with server's user)
     * Return null on good or error message on bad
     */
    public String connectUserHandling(String userName, String password, String restoreSessionId) {
        this.isAdmin = false;

        // find auth user
        AuthorizedUser authorizedUser = null;
        if (managerFactory.configSettings().isAuthenticationActivated()) {
            authorizedUser = AuthorizedUserRepository.getInstance().getByName(userName);
            String errorMsg = "Wrong username or password. You must register your account first.";
            if (authorizedUser == null) {
                return errorMsg;
            }

            if (!Main.isTestMode() && !authorizedUser.doCredentialsMatch(userName, password)) {
                return errorMsg;
            }

            if (!authorizedUser.active) {
                return "Your profile has been deactivated by admin.";
            }
            if (authorizedUser.lockedUntil != null) {
                if (authorizedUser.lockedUntil.compareTo(Calendar.getInstance().getTime()) > 0) {
                    return "Your profile has need deactivated by admin until " + SystemUtil.dateFormat.format(authorizedUser.lockedUntil);
                } else {
                    // unlock on timeout end
                    managerFactory.userManager().createUser(userName, host, authorizedUser).ifPresent(user
                            -> user.setLockedUntil(null)
                    );
                }
            }
        }

        // create new user instance (auth or anon)
        boolean isReconnection = false;
        User newUser = managerFactory.userManager().createUser(userName, host, authorizedUser).orElse(null);

        // if user instance already exists then keep only one instance
        if (newUser == null) {
            User anotherUser = managerFactory.userManager().getUserByName(userName).orElse(null);
            if (anotherUser != null) {
                boolean canDisconnectAuthDueAnotherInstance = managerFactory.configSettings().isAuthenticationActivated();
                boolean canDisconnectAnonDueSameHost = !managerFactory.configSettings().isAuthenticationActivated()
                        && ANON_IDENTIFY_BY_HOST
                        && Objects.equals(anotherUser.getHost(), host);
                boolean canDisconnectAnyDueSessionRestore = Objects.equals(restoreSessionId, anotherUser.getRestoreSessionId());
                if (canDisconnectAuthDueAnotherInstance
                        || canDisconnectAnonDueSameHost
                        || canDisconnectAnyDueSessionRestore) {
                    anotherUser.updateLastActivity(null);  // minimizes possible expiration

                    // disconnect another user instance, but keep all active tables
                    if (!anotherUser.getSessionId().isEmpty()) {
                        // do not use DisconnectReason.Disconnected here - it will remove user from all tables,
                        // but it must remove only session without any tables
                        String instanceReason = canDisconnectAnyDueSessionRestore ? " (session reconnect)"
                                : canDisconnectAnonDueSameHost ? " (same host)"
                                : canDisconnectAuthDueAnotherInstance ? " (same user)" : "";
                        String mes = "";
                        mes += String.format("Disconnecting another user instance for %s (reason: %s)", userName, instanceReason);
                        if (logger.isDebugEnabled()) {
                            mes += String.format("\n - reason: auth (%s), anon host (%s), any session (%s)",
                                    (canDisconnectAuthDueAnotherInstance ? "yes" : "no"),
                                    (canDisconnectAnonDueSameHost ? "yes" : "no"),
                                    (canDisconnectAnyDueSessionRestore ? "yes" : "no")
                            );
                            mes += String.format("\n - sessionId: %s => %s", anotherUser.getSessionId(), sessionId);
                            mes += String.format("\n - name: %s => %s", anotherUser.getName(), userName);
                            mes += String.format("\n - host: %s => %s", anotherUser.getHost(), host);
                        }
                        logger.info(mes);

                        // kill another instance
                        DisconnectReason reason = DisconnectReason.AnotherUserInstance;
                        if (ANON_IDENTIFY_BY_HOST && Objects.equals(anotherUser.getHost(), host)) {
                            // if user reconnects by itself then must hide another instance message
                            reason = DisconnectReason.AnotherUserInstanceSilent;
                        }

                        managerFactory.userManager().disconnect(anotherUser.getId(), reason);
                    }
                    isReconnection = true;
                    newUser = anotherUser;
                } else {
                    return "User " + userName + " already connected or your IP address changed - try another user";
                }
            } else {
                // code never goes here
                return "Can't find connected user name " + userName;
            }
        }

        // link user with current session
        newUser.setRestoreSessionId("");
        this.userId = newUser.getId();
        if (!managerFactory.userManager().connectToSession(sessionId, this.userId)) {
            return "Error link user " + userName + " with session " + sessionId;
        }

        // connect to lobby (other chats must be joined from a client side on table panel creating process)
        GamesRoom lobby = managerFactory.gamesRoomManager().getRoom(managerFactory.gamesRoomManager().getMainRoomId()).orElse(null);
        if (lobby != null) {
            managerFactory.chatManager().joinChat(lobby.getChatId(), this.userId);
        } else {
            // TODO: outdated code? Need research server logs and fix or delete it, 2023-12-04
            logger.warn("main room not found"); // after server restart users try to use old rooms on reconnect for some reason
        }

        // all fine
        newUser.setUserState(User.UserState.Connected);
        newUser.setRestoreSessionId(newUser.getSessionId());

        // restore all active tables
        // run in diff thread, so user will be connected anyway (e.g. on some errors in onReconnect)
        final User reconnectUser = newUser;
        if (isReconnection) {
            managerFactory.threadExecutor().getCallExecutor().execute(reconnectUser::onReconnect);
        }

        // inform about reconnection
        if (isReconnection) {
            managerFactory.chatManager().sendReconnectMessage(this.userId);
        }

        return null;
    }

    public void connectAdmin() {
        this.isAdmin = true;
        User user = managerFactory.userManager().createUser(User.ADMIN_NAME, host, null)
                .orElse(managerFactory.userManager().getUserByName(User.ADMIN_NAME).get());
        UserData adminUserData = UserData.getDefaultUserDataView();
        adminUserData.setGroupId(UserGroup.ADMIN.getGroupId());
        user.setUserData(adminUserData);
        if (!managerFactory.userManager().connectToSession(sessionId, user.getId())) {
            logger.info("Error connecting as admin");
        } else {
            user.setUserState(User.UserState.Connected);
        }
        this.userId = user.getId();
    }

    public boolean setUserData(String userName, UserData userData, String clientVersion, String userIdStr) {
        Optional<User> _user = managerFactory.userManager().getUserByName(userName);
        _user.ifPresent(user -> {
            if (clientVersion != null) {
                user.setClientVersion(clientVersion);
            }
            user.setUserIdStr(userIdStr);
            if (user.getUserData() == null || user.getUserData().getGroupId() == UserGroup.DEFAULT.getGroupId()) {
                user.setUserData(userData);
            } else {
                user.getUserData().update(userData);
            }
            if (user.getUserData().getAvatarId() < Constants.MIN_AVATAR_ID
                    || user.getUserData().getAvatarId() > Constants.MAX_AVATAR_ID) {
                user.getUserData().setAvatarId(Constants.DEFAULT_AVATAR_ID);
            }
            if (user.getUserData().getAvatarId() == 11) {
                user.getUserData().setAvatarId(updateAvatar(user.getName()));
            }
        });
        return _user.isPresent();
    }

    private int updateAvatar(String userName) {
        //TODO: move to separate class
        //TODO: add for checking for private key
        switch (userName) {
            case "North":
                return 1006;
            case "BetaSteward":
                return 1008;
            case "Bandit":
                return 1020;
            case "fireshoes":
                return 1021;
            case "noxx":
                return 1000;
            case "magenoxx":
                return 1018;
        }
        return 11;
    }

    public String getId() {
        return sessionId;
    }

    /**
     * Send event/command to the client
     */
    public void fireCallback(final ClientCallback call) {
        boolean lockSet = false; // TODO: research about locks, why it here? 2023-12-06
        try {
            if (valid && callBackLock.tryLock(50, TimeUnit.MILLISECONDS)) {
                call.setMessageId(messageId.incrementAndGet());
                lockSet = true;
                Callback callback = new Callback(call);
                boolean sendAsync = SUPER_DUPER_BUGGY_AND_FASTEST_ASYNC_CONNECTION
                        && call.getMethod().getType().canComeInAnyOrder();
                callbackHandler.handleCallbackOneway(callback, sendAsync);
            }
        } catch (InterruptedException ex) {
            // already sending another command (connection problem?)
            if (call.getMethod().equals(ClientCallbackMethod.GAME_INIT)
                    || call.getMethod().equals(ClientCallbackMethod.START_GAME)) {
                // it's ok use case, user has connection problem so can't send game init (see sendInfoAboutPlayersNotJoinedYetAndTryToFixIt)
            } else {
                logger.warn("SESSION LOCK, possible connection problem - fireCallback - userId: " + userId + " messageId: " + call.getMessageId(), ex);
            }
        } catch (HandleCallbackException ex) {
            // general error
            // can raise on server freeze or normal connection problem from a client side
            // no need to print a full stack log here
            logger.warn("SESSION CALLBACK EXCEPTION - " + ThreadUtils.findRootException(ex) + ", userId " + userId + ", messageId: " + call.getMessageId());

            // do not send data anymore (user must reconnect)
            this.valid = false;
            managerFactory.sessionManager().disconnect(sessionId, DisconnectReason.LostConnection, true);
        } catch (Throwable ex) {
            logger.error("SESSION CALLBACK UNKNOWN EXCEPTION - " + ThreadUtils.findRootException(ex) + ", userId " + userId + ", messageId: " + call.getMessageId(), ex);

            // do not send data anymore (user must reconnect)
            this.valid = false;
            managerFactory.sessionManager().disconnect(sessionId, DisconnectReason.LostConnection, true);
        } finally {
            if (lockSet) {
                callBackLock.unlock();
            }
        }
    }

    public UUID getUserId() {
        return userId;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public String getHost() {
        return host;
    }

    public Date getConnectionTime() {
        return timeConnected;
    }

    void setHost(String hostAddress) {
        this.host = hostAddress;
    }

    public void sendErrorMessageToClient(String message) {
        List<String> messageData = new LinkedList<>();
        messageData.add("Error while connecting to server");
        messageData.add(message);
        fireCallback(new ClientCallback(ClientCallbackMethod.SHOW_USERMESSAGE, null, messageData));
    }

    public void sendInfoMessageToClient(String message) {
        List<String> messageData = new LinkedList<>();
        messageData.add("Information about connecting to the server");
        messageData.add(message);
        fireCallback(new ClientCallback(ClientCallbackMethod.SHOW_USERMESSAGE, null, messageData));
    }

    public static Throwable getBasicCause(Throwable cause) {
        Throwable t = cause;
        while (t.getCause() != null) {
            t = t.getCause();
            if (Objects.equals(t, cause)) {
                throw new IllegalArgumentException("Infinite cycle detected in causal chain");
            }
        }
        return t;
    }
}

class RandomString {

    private static final char[] symbols;

    static {
        StringBuilder tmp = new StringBuilder();
        for (char ch = '0'; ch <= '9'; ++ch) {
            tmp.append(ch);
        }
        for (char ch = 'a'; ch <= 'z'; ++ch) {
            tmp.append(ch);
        }
        symbols = tmp.toString().toCharArray();
    }

    private final char[] buf;

    public RandomString(int length) {
        if (length < 8) {
            length = 8;
        }
        buf = new char[length];
    }

    public String nextString() {
        for (int idx = 0; idx < buf.length; ++idx) {
            buf[idx] = symbols[RandomUtil.nextInt(symbols.length)];
        }
        return new String(buf);
    }
}
