package mage.server;

import mage.constants.Constants;
import mage.remote.DisconnectReason;
import mage.util.RandomUtil;
import mage.players.net.UserData;
import mage.players.net.UserGroup;
import mage.remote.Connection;
import mage.server.managers.ConfigSettings;
import mage.server.managers.ManagerFactory;
import mage.server.game.GamesRoom;
import mage.server.util.SystemUtil;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class Session {

    private static final Logger logger = Logger.getLogger(Session.class);
    private static final Pattern alphabetsPattern = Pattern.compile("[a-zA-Z]");
    private static final Pattern digitsPattern = Pattern.compile("[0-9]");
    private static final ScheduledExecutorService pingTaskExecutor = Executors.newScheduledThreadPool(10);

    public static final String REGISTRATION_DISABLED_MESSAGE = "Registration has been disabled on the server. You can use any name and empty password to login.";

    private final ManagerFactory managerFactory;
    private final String sessionId;
    private UUID userId;
    private String host;
    private final AtomicInteger messageId = new AtomicInteger(0);
    private final Date timeConnected;
    private boolean isAdmin = false;
    private final static int PING_CYCLES = 10;
    private final LinkedList<Long> pingTime = new LinkedList<>();
    private String pingInfo = "";
    private boolean valid = true;

    private final ReentrantLock lock;
    private final ReentrantLock callBackLock;

    public Session(ManagerFactory managerFactory, String sessionId) {
        this.managerFactory = managerFactory;
        this.sessionId = sessionId;
        this.isAdmin = false;
        this.timeConnected = new Date();
        this.lock = new ReentrantLock();
        this.callBackLock = new ReentrantLock();
    }

    public String registerUser(Connection connection){
        if (!managerFactory.configSettings().isAuthenticationActivated()) {
            String returnMessage = REGISTRATION_DISABLED_MESSAGE;
            return returnMessage;
        }
        synchronized (AuthorizedUserRepository.getInstance()) {
            // name
            String userName = connection.getUsername();
            String password = connection.getPassword();
            String email = connection.getEmail();
            String returnMessage = validateUserName(userName);
            if (returnMessage != null) {
                return returnMessage;
            }

            // auto-generated password
            RandomString randomString = new RandomString(10);
            password = randomString.nextString();
            returnMessage = validatePassword(password, userName);
            if (returnMessage != null) {
                return returnMessage;
            }

            // email
            returnMessage = validateEmail(email);
            if (returnMessage != null) {
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
            } else if (Main.isTestMode()) {
                String ok = "Email sending failed. Server is in test mode. Your account registered with a password " + password + " for a user " + userName;
                logger.info(ok);
            } else {
                String err = "Email sending failed. Try use another email address or service. Or reset password by email " + email + " for a user " + userName;
                logger.error(err);
                return err;
            }
            return null;
        }
    }

    private String validateUserName(String userName) {
        // return error message or null on good name

        if (userName.equals("Admin")) {
            // virtual user for admin console
            return "User name Admin already in use";
        }

        ConfigSettings config = managerFactory.configSettings();
        if (userName.length() < config.getMinUserNameLength()) {
            return "User name may not be shorter than " + config.getMinUserNameLength() + " characters";
        }
        if (userName.length() > config.getMaxUserNameLength()) {
            return "User name may not be longer than " + config.getMaxUserNameLength() + " characters";
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

    public String connectUser(String userName, String password){
        String returnMessage = connectUserHandling(userName, password);
        return returnMessage;
    }
    
    public String connectUserHandling(String userName, String password)  {
        this.isAdmin = false;
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

        Optional<User> selectUser = managerFactory.userManager().createUser(userName, host, authorizedUser);
        boolean reconnect = false;
        if (!selectUser.isPresent()) {
            // user already connected
            selectUser = managerFactory.userManager().getUserByName(userName);
            if (selectUser.isPresent()) {
                User user = selectUser.get();
                // If authentication is not activated, check the identity using IP address.
                if (managerFactory.configSettings().isAuthenticationActivated() || user.getHost().equals(host)) {
                    user.updateLastActivity(null);  // minimizes possible expiration
                    this.userId = user.getId();
                    if (user.getSessionId().isEmpty()) {
                        logger.info("Reconnecting session for " + userName);
                        reconnect = true;
                    } else {
                        //disconnect previous session
                        logger.info("Disconnecting another user instance: " + userName);
                        managerFactory.sessionManager().disconnect(user.getSessionId(), DisconnectReason.ConnectingOtherInstance);
                    }
                } else {
                    return "User name " + userName + " already in use (or your IP address changed)";
                }
            } else {
                // code never goes here
                return "Can't find connected user name " + userName;
            }
        }
        User user = selectUser.get();
        if (!managerFactory.userManager().connectToSession(sessionId, user.getId())) {
            return "Error connecting " + userName;
        }
        this.userId = user.getId();
        if (reconnect) { // must be connected to receive the message
            Optional<GamesRoom> room = managerFactory.gamesRoomManager().getRoom(managerFactory.gamesRoomManager().getMainRoomId());
            if (!room.isPresent()) {
                logger.warn("main room not found"); // after server restart users try to use old rooms on reconnect
                return null;
            }
            managerFactory.chatManager().joinChat(room.get().getChatId(), userId);
            managerFactory.chatManager().sendReconnectMessage(userId);
        }

        return null;
    }

    public boolean isLocked() {
        return lock.isLocked();
    }


    public void connectAdmin() {
        this.isAdmin = true;
        User user = managerFactory.userManager().createUser("Admin", host, null).orElse(
                managerFactory.userManager().getUserByName("Admin").get());
        UserData adminUserData = UserData.getDefaultUserDataView();
        adminUserData.setGroupId(UserGroup.ADMIN.getGroupId());
        user.setUserData(adminUserData);
        if (!managerFactory.userManager().connectToSession(sessionId, user.getId())) {
            logger.info("Error connecting Admin!");
        } else {
            user.setUserState(User.UserState.Connected);
        }
        this.userId = user.getId();
    }

    public boolean setUserData(User user, UserData userData, String clientVersion, String userIdStr) {
        if (user != null) {
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
            return true;
        }
        return false;
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
        Optional<User> _user = managerFactory.userManager().getUser(userId);
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
            managerFactory.userManager().removeUserFromAllTablesAndChat(userId, reason);
            pingTime.clear();
        } catch (InterruptedException ex) {
            logger.error("SESSION LOCK - kill: userId " + userId, ex);
        } finally {
            if (lockSet) {
                lock.unlock();
                logger.debug("SESSION LOCK UNLOCK sessionId: " + sessionId);

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

    public void recordPingTime(long milliSeconds) {
        pingTime.add(milliSeconds);
        String lastPing = milliSeconds > 0 ? milliSeconds + "ms" : "<1ms";
        if (pingTime.size() > PING_CYCLES) {
            pingTime.poll();
        }
        long sum = 0;
        for (Long time : pingTime) {
            sum += time;
        }
        long avg = sum / pingTime.size();
        pingInfo = lastPing + " (Av: " + (avg > 0 ? avg + "ms" : "<1ms") + ")";
    }

    public String getPingInfo() {
        return pingInfo;
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
