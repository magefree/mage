
package mage.server;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import mage.MageException;
import mage.constants.Constants;
import mage.interfaces.callback.ClientCallback;
import mage.interfaces.callback.ClientCallbackMethod;
import mage.players.net.UserData;
import mage.players.net.UserGroup;
import static mage.server.DisconnectReason.LostConnection;
import mage.server.game.GamesRoom;
import mage.server.game.GamesRoomManager;
import mage.server.util.ConfigSettings;
import mage.server.util.SystemUtil;
import mage.util.RandomUtil;
import org.apache.log4j.Logger;
import org.jboss.remoting.callback.AsynchInvokerCallbackHandler;
import org.jboss.remoting.callback.Callback;
import org.jboss.remoting.callback.HandleCallbackException;
import org.jboss.remoting.callback.InvokerCallbackHandler;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class Session {

    private static final Logger logger = Logger.getLogger(Session.class);
    private final static Pattern alphabetsPattern = Pattern.compile("[a-zA-Z]");
    private final static Pattern digitsPattern = Pattern.compile("[0-9]");

    private final String sessionId;
    private UUID userId;
    private String host;
    private int messageId = 0;
    private final Date timeConnected;
    private boolean isAdmin = false;
    private final AsynchInvokerCallbackHandler callbackHandler;
    private boolean valid = true;

    private final ReentrantLock lock;
    private final ReentrantLock callBackLock;

    public Session(String sessionId, InvokerCallbackHandler callbackHandler) {
        this.sessionId = sessionId;
        this.callbackHandler = (AsynchInvokerCallbackHandler) callbackHandler;
        this.isAdmin = false;
        this.timeConnected = new Date();
        this.lock = new ReentrantLock();
        this.callBackLock = new ReentrantLock();
    }

    public String registerUser(String userName, String password, String email) throws MageException {
        if (!ConfigSettings.instance.isAuthenticationActivated()) {
            String returnMessage = "Registration is disabled by the server config";
            sendErrorMessageToClient(returnMessage);
            return returnMessage;
        }
        synchronized (AuthorizedUserRepository.instance) {
            String returnMessage = validateUserName(userName);
            if (returnMessage != null) {
                sendErrorMessageToClient(returnMessage);
                return returnMessage;
            }

            RandomString randomString = new RandomString(10);
            password = randomString.nextString();
            returnMessage = validatePassword(password, userName);
            if (returnMessage != null) {
                sendErrorMessageToClient(returnMessage);
                return returnMessage;
            }
            returnMessage = validateEmail(email);
            if (returnMessage != null) {
                sendErrorMessageToClient(returnMessage);
                return returnMessage;
            }
            AuthorizedUserRepository.instance.add(userName, password, email);
            String text = "You are successfully registered as " + userName + '.';
            text += "  Your initial, generated password is: " + password;

            boolean success;
            String subject = "XMage Registration Completed";
            if (!ConfigSettings.instance.getMailUser().isEmpty()) {
                success = MailClient.sendMessage(email, subject, text);
            } else {
                success = MailgunClient.sendMessage(email, subject, text);
            }
            if (success) {
                String ok = "Sent a registration confirmation / initial password email to " + email + " for " + userName;
                logger.info(ok);
                sendInfoMessageToClient(ok);
            } else if (Main.isTestMode()) {
                String ok = "Server is in test mode.  Your account is registered with a password of " + password + " for " + userName;
                logger.info(ok);
                sendInfoMessageToClient(ok);
            } else {
                String err = "Failed sending a registration confirmation / initial password email to " + email + " for " + userName;
                logger.error(err);
                sendErrorMessageToClient(err);
                return err;
            }
            return null;
        }
    }

    private static String validateUserName(String userName) {
        if (userName.equals("Admin")) {
            return "User name Admin already in use";
        }
        ConfigSettings config = ConfigSettings.instance;
        if (userName.length() < config.getMinUserNameLength()) {
            return "User name may not be shorter than " + config.getMinUserNameLength() + " characters";
        }
        if (userName.length() > config.getMaxUserNameLength()) {
            return "User name may not be longer than " + config.getMaxUserNameLength() + " characters";
        }
        Pattern invalidUserNamePattern = Pattern.compile(ConfigSettings.instance.getInvalidUserNamePattern(), Pattern.CASE_INSENSITIVE);
        Matcher m = invalidUserNamePattern.matcher(userName);
        if (m.find()) {
            return "User name '" + userName + "' includes not allowed characters: use a-z, A-Z and 0-9";
        }
        AuthorizedUser authorizedUser = AuthorizedUserRepository.instance.getByName(userName);
        if (authorizedUser != null) {
            return "User name '" + userName + "' already in use";
        }
        return null;
    }

    private static String validatePassword(String password, String userName) {
        ConfigSettings config = ConfigSettings.instance;
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
        AuthorizedUser authorizedUser = AuthorizedUserRepository.instance.getByEmail(email);
        if (authorizedUser != null) {
            return "Email address '" + email + "' is associated with another user";
        }
        return null;
    }

    public String connectUser(String userName, String password) throws MageException {
        String returnMessage = connectUserHandling(userName, password);
        if (returnMessage != null) {
            sendErrorMessageToClient(returnMessage);
        }
        return returnMessage;
    }

    public boolean isLocked() {
        return lock.isLocked();
    }

    public String connectUserHandling(String userName, String password) throws MageException {
        this.isAdmin = false;
        AuthorizedUser authorizedUser = null;
        if (ConfigSettings.instance.isAuthenticationActivated()) {
            authorizedUser = AuthorizedUserRepository.instance.getByName(userName);
            String errorMsg = "Wrong username or password. In case you haven't, please register your account first.";
            if (authorizedUser == null) {
                return errorMsg;
            }

            if (!Main.isTestMode() && !authorizedUser.doCredentialsMatch(userName, password)) {
                return errorMsg;
            }

            if (!authorizedUser.active) {
                return "Your profile is deactivated, you can't sign on.";
            }
            if (authorizedUser.lockedUntil != null) {
                if (authorizedUser.lockedUntil.compareTo(Calendar.getInstance().getTime()) > 0) {
                    return "Your profile is deactivated until " + SystemUtil.dateFormat.format(authorizedUser.lockedUntil);
                } else {
                    UserManager.instance.createUser(userName, host, authorizedUser).ifPresent(user
                            -> user.setLockedUntil(null)
                    );

                }
            }
        }
        Optional<User> selectUser = UserManager.instance.createUser(userName, host, authorizedUser);
        boolean reconnect = false;
        if (!selectUser.isPresent()) {  // user already exists
            selectUser = UserManager.instance.getUserByName(userName);
            if (selectUser.isPresent()) {
                User user = selectUser.get();
                // If authentication is not activated, check the identity using IP address.
                if (ConfigSettings.instance.isAuthenticationActivated() || user.getHost().equals(host)) {
                    user.updateLastActivity(null);  // minimizes possible expiration
                    this.userId = user.getId();
                    if (user.getSessionId().isEmpty()) {
                        logger.info("Reconnecting session for " + userName);
                        reconnect = true;
                    } else {
                        //disconnect previous session
                        logger.info("Disconnecting another user instance: " + userName);
                        SessionManager.instance.disconnect(user.getSessionId(), DisconnectReason.ConnectingOtherInstance);
                    }
                } else {
                    return "User name " + userName + " already in use (or your IP address changed)";
                }
            }
        }
        User user = selectUser.get();
        if (!UserManager.instance.connectToSession(sessionId, user.getId())) {
            return "Error connecting " + userName;
        }
        this.userId = user.getId();
        if (reconnect) { // must be connected to receive the message
            Optional<GamesRoom> room = GamesRoomManager.instance.getRoom(GamesRoomManager.instance.getMainRoomId());
            if (!room.isPresent()) {
                logger.error("main room not found");
                return null;
            }
            ChatManager.instance.joinChat(room.get().getChatId(), userId);
            ChatManager.instance.sendReconnectMessage(userId);
        }

        return null;

    }

    public void connectAdmin() {
        this.isAdmin = true;
        User user = UserManager.instance.createUser("Admin", host, null).orElse(
                UserManager.instance.getUserByName("Admin").get());
        UserData adminUserData = UserData.getDefaultUserDataView();
        adminUserData.setGroupId(UserGroup.ADMIN.getGroupId());
        user.setUserData(adminUserData);
        if (!UserManager.instance.connectToSession(sessionId, user.getId())) {
            logger.info("Error connecting Admin!");
        } else {
            user.setUserState(User.UserState.Connected);
        }
        this.userId = user.getId();
    }

    public boolean setUserData(String userName, UserData userData, String clientVersion, String userIdStr) {
        Optional<User> _user = UserManager.instance.getUserByName(userName);
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

    // because different threads can activate this
    public void userLostConnection() {
        Optional<User> _user = UserManager.instance.getUser(userId);
        if (!_user.isPresent()) {
            return; //user was already disconnected by other thread
        }
        User user = _user.get();
        if (!user.isConnected()) {
            return;
        }
        if (!user.getSessionId().equals(sessionId)) {
            // user already reconnected with another instance
            logger.info("OLD SESSION IGNORED - " + user.getName());
        } else {
            // logger.info("LOST CONNECTION - " + user.getName() + " id: " + userId);
        }
    }

    public void kill(DisconnectReason reason) {
        boolean lockSet = false;
        try {
            if (lock.tryLock(5000, TimeUnit.MILLISECONDS)) {
                lockSet = true;
                logger.debug("SESSION LOCK SET sessionId: " + sessionId);
            } else {
                logger.error("SESSION LOCK - kill: userId " + userId);
            }
            UserManager.instance.removeUserFromAllTablesAndChat(userId, reason);
        } catch (InterruptedException ex) {
            logger.error("SESSION LOCK - kill: userId " + userId, ex);
        } finally {
            if (lockSet) {
                lock.unlock();
                logger.debug("SESSION LOCK UNLOCK sessionId: " + sessionId);

            }
        }

    }

    public void fireCallback(final ClientCallback call) {
        boolean lockSet = false;
        try {
            if (valid && callBackLock.tryLock(50, TimeUnit.MILLISECONDS)) {
                call.setMessageId(messageId++);
                lockSet = true;
                callbackHandler.handleCallbackOneway(new Callback(call));
            }
        } catch (InterruptedException ex) {
            logger.warn("SESSION LOCK - fireCallback - userId: " + userId + " messageId: " + call.getMessageId(), ex);
        } catch (HandleCallbackException ex) {
            this.valid = false;
            UserManager.instance.getUser(userId).ifPresent(user -> {
                user.setUserState(User.UserState.Disconnected);
                logger.warn("SESSION CALLBACK EXCEPTION - " + user.getName() + " userId " + userId + " messageId: " + call.getMessageId() + " - cause: " + getBasicCause(ex).toString());
                logger.trace("Stack trace:", ex);
                SessionManager.instance.disconnect(sessionId, LostConnection);
            });
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
