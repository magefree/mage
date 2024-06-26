package mage.server;

import mage.server.managers.ManagerFactory;
import mage.server.managers.UserManager;
import mage.server.record.UserStats;
import mage.server.record.UserStatsRepository;
import mage.server.util.ServerMessagesUtil;
import mage.util.ThreadUtils;
import mage.util.XMageThreadFactory;
import mage.view.UserView;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Server: manage active user instances (connected to the server)
 *
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class UserManagerImpl implements UserManager {

    // timeouts on user's activity (on connection problems)
    private static final int USER_CONNECTION_TIMEOUTS_CHECK_SECS = 30;
    private static final int USER_CONNECTION_TIMEOUT_INFORM_AFTER_SECS = 30; // inform user's opponents about problem
    private static final int USER_CONNECTION_TIMEOUT_SESSION_EXPIRE_AFTER_SECS = 3 * 60; // session expire - remove from all tables and chats (can't reconnect after it)
    private static final int USER_CONNECTION_TIMEOUT_REMOVE_FROM_SERVER_SECS = 8 * 60; // removes from users list

    private static final int SERVER_USERS_LIST_UPDATE_SECS = 10; // server side updates (client use own timeouts to request users list)

    private static final Logger logger = Logger.getLogger(UserManagerImpl.class);

    protected final ScheduledExecutorService CONNECTION_EXPIRED_EXECUTOR = Executors.newSingleThreadScheduledExecutor(
            new XMageThreadFactory(ThreadUtils.THREAD_PREFIX_SERVICE_CONNECTION_EXPIRED_CHECK)
    );
    protected final ScheduledExecutorService USERS_LIST_REFRESH_EXECUTOR = Executors.newSingleThreadScheduledExecutor(
            new XMageThreadFactory(ThreadUtils.THREAD_PREFIX_SERVICE_USERS_LIST_REFRESH)
    );

    private List<UserView> userInfoList = new ArrayList<>(); // all users list for main room/chat
    private int maxUsersOnline = 0;
    private final ManagerFactory managerFactory;


    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final ConcurrentHashMap<UUID, User> users = new ConcurrentHashMap<>();

    private ExecutorService USER_EXECUTOR;

    public UserManagerImpl(ManagerFactory managerFactory) {
        this.managerFactory = managerFactory;
    }

    public void init() {
        USER_EXECUTOR = managerFactory.threadExecutor().getCallExecutor();
        CONNECTION_EXPIRED_EXECUTOR.scheduleAtFixedRate(this::checkExpired, USER_CONNECTION_TIMEOUTS_CHECK_SECS, USER_CONNECTION_TIMEOUTS_CHECK_SECS, TimeUnit.SECONDS);
        USERS_LIST_REFRESH_EXECUTOR.scheduleAtFixedRate(this::updateUserInfoList, SERVER_USERS_LIST_UPDATE_SECS, SERVER_USERS_LIST_UPDATE_SECS, TimeUnit.SECONDS);
    }

    @Override
    public Optional<User> createUser(String userName, String host, AuthorizedUser authorizedUser) {
        if (getUserByName(userName).isPresent()) {
            return Optional.empty(); //user already exists
        }
        User user = new User(managerFactory, userName, host, authorizedUser);
        final Lock w = lock.writeLock();
        w.lock();
        try {
            users.put(user.getId(), user);
        } finally {
            w.unlock();
        }
        return Optional.of(user);
    }

    @Override
    public Optional<User> getUser(UUID userId) {
        if (userId == null) {
            return Optional.empty();
        }

        final Lock r = lock.readLock();
        r.lock();
        try {
            return Optional.ofNullable(users.getOrDefault(userId, null));
        } finally {
            r.unlock();
        }
    }

    @Override
    public Optional<User> getUserByName(String userName) {
        final Lock r = lock.readLock();
        r.lock();
        try {
            return users.values()
                    .stream()
                    .filter(user -> user.getName().equals(userName))
                    .findFirst();
        } finally {
            r.unlock();
        }
    }

    @Override
    public Collection<User> getUsers() {
        List<User> userList = new ArrayList<>();
        final Lock r = lock.readLock();
        r.lock();
        try {
            userList.addAll(users.values());
        } finally {
            r.unlock();
        }
        return userList;
    }

    @Override
    public boolean connectToSession(String sessionId, UUID userId) {
        if (userId != null) {
            Optional<User> user = getUser(userId);
            if (user.isPresent()) {
                user.get().setSessionId(sessionId);
                return true;
            }
        }
        return false;
    }

    @Override
    public void disconnect(UUID userId, DisconnectReason reason) {
        User user = getUser(userId).orElse(null);
        if (user != null) {
            user.onLostConnection(reason);
        }
    }

    @Override
    public boolean isAdmin(UUID userId) {
        return getUser(userId)
                .filter(u -> u.getName().equals(User.ADMIN_NAME))
                .isPresent();
    }

    @Override
    public void informUserOpponents(final UUID userId, final String message) {
        if (userId != null) {
            getUser(userId).ifPresent(user
                    -> USER_EXECUTOR.execute(
                    () -> {
                        try {
                            managerFactory.chatManager().sendMessageToUserChats(user.getId(), message);
                        } catch (Exception ex) {
                            handleException(ex);
                        }
                    }
            ));
        }
    }

    @Override
    public boolean extendUserSession(UUID userId, String pingInfo) {
        if (userId != null) {
            User user = users.get(userId);
            if (user != null) {
                user.updateLastActivity(pingInfo);
                return true;
            }
        }
        return false;
    }

    /**
     * Is the connection lost for more than 3 minutes, the user will be set to
     * offline status. The user will be removed in validity check after 8
     * minutes of no activities
     */
    private void checkExpired() {
        try {
            Calendar calInform = Calendar.getInstance();
            calInform.add(Calendar.SECOND, -1 * USER_CONNECTION_TIMEOUT_INFORM_AFTER_SECS);
            Calendar calSessionExpire = Calendar.getInstance();
            calSessionExpire.add(Calendar.SECOND, -1 * USER_CONNECTION_TIMEOUT_SESSION_EXPIRE_AFTER_SECS);
            Calendar calUserRemove = Calendar.getInstance();
            calUserRemove.add(Calendar.SECOND, -1 * USER_CONNECTION_TIMEOUT_REMOVE_FROM_SERVER_SECS);
            List<User> usersToRemove = new ArrayList<>();
            logger.debug("Start Check Expired");
            List<User> userList = new ArrayList<>();
            final Lock r = lock.readLock();
            r.lock();
            try {
                userList.addAll(users.values());
            } finally {
                r.unlock();
            }
            for (User user : userList) {
                try {
                    // expire logic:
                    // - any user actions will update user's last activity date
                    // - expire code schedules to check last activity every few seconds (minutes)
                    // - if something outdated then it will be removed from a server
                    // - user lifecycle: created -> connected/disconnected (bad connection, bad session) -> offline

                    boolean isBadConnection = user.isExpired(calInform.getTime());
                    boolean isBadSession = user.isExpired(calSessionExpire.getTime());
                    boolean isBadUser = user.isExpired(calUserRemove.getTime());

                    switch (user.getUserState()) {
                        case Created: {
                            // ignore
                            break;
                        }

                        case Offline: {
                            // remove user from a server (users list for GUI)
                            if (isBadUser) {
                                usersToRemove.add(user);
                            }
                            break;
                        }

                        case Connected:
                        case Disconnected: {
                            if (isBadConnection) {
                                long secsInfo = (Calendar.getInstance().getTimeInMillis() - user.getLastActivity().getTime()) / 1000;
                                informUserOpponents(user.getId(), String.format("%s catch connection problems for %s secs (left before expire: %d secs)",
                                        user.getName(),
                                        secsInfo,
                                        Math.max(0, USER_CONNECTION_TIMEOUT_SESSION_EXPIRE_AFTER_SECS - secsInfo)
                                ));
                            }
                            if (isBadSession) {
                                // full disconnect
                                disconnect(user.getId(), DisconnectReason.SessionExpired);
                            }
                            break;
                        }

                        default: {
                            throw new IllegalArgumentException("Unknown user state: " + user.getUserState());
                        }
                    }
                } catch (Exception ex) {
                    handleException(ex);
                }
            }
            logger.debug("Users to remove " + usersToRemove.size());
            final Lock w = lock.writeLock();
            w.lock();
            try {
                for (User user : usersToRemove) {
                    users.remove(user.getId());
                }
            } finally {
                w.unlock();
            }
            logger.debug("End Check Expired");
        } catch (Exception ex) {
            handleException(ex);
        }
    }

    /**
     * Remove user instance from a server
     * Warning, call it after all tables/chats and other user related data removes
     *
     * @param userId
     */
    @Override
    public void removeUser(UUID userId) {
        try {
            final Lock w = lock.writeLock();
            w.lock();
            try {
                users.remove(userId);
            } finally {
                w.unlock();
            }
        } catch (Exception e) {
            handleException(e);
        }

    }

    /**
     * This method recreated the user list that will be sent to all clients
     */
    private void updateUserInfoList() {
        try {
            List<UserView> newUserInfoList = new ArrayList<>();
            int currentOnlineCount = 0;
            for (User user : getUsers()) {
                newUserInfoList.add(new UserView(
                        user.getName(),
                        user.getHost(),
                        user.getSessionId(),
                        user.getConnectionTime(),
                        user.getLastActivity(),
                        user.getGameInfo(),
                        user.getUserState().toString(),
                        user.getChatLockedUntil(),
                        user.getClientVersion(),
                        user.getEmail(),
                        user.getUserIdStr()
                ));

                if (user.isOnlineUser()) {
                    currentOnlineCount++;
                }
            }
            userInfoList = newUserInfoList;

            // max users online stats
            if (currentOnlineCount > maxUsersOnline) {
                maxUsersOnline = currentOnlineCount;
                // TODO: if server get too much logs after restart (on massive reconnect) then add logs timeout here
                logger.info(String.format("New max users online: %d", maxUsersOnline));
                ServerMessagesUtil.instance.setMaxUsersOnline(maxUsersOnline); // update online stats for news panel
            }
        } catch (Exception ex) {
            handleException(ex);
        }
    }

    @Override
    public List<UserView> getUserInfoList() {
        return userInfoList;
    }

    @Override
    public void handleException(Exception ex) {
        if (ex != null) {
            logger.fatal("User manager exception ", ex);
            if (ex.getStackTrace() != null) {
                logger.fatal(ex.getStackTrace());
            }
        } else {
            logger.fatal("User manager exception - null");
        }
    }

    @Override
    public String getUserHistory(String userName) {
        Optional<User> user = getUserByName(userName);
        if (user.isPresent()) {
            return "History of user " + userName + " - " + user.get().getUserData().getHistory();
        }

        UserStats userStats = UserStatsRepository.instance.getUser(userName);
        if (userStats != null) {
            return "History of user " + userName + " - " + User.userStatsToHistory(userStats.getProto());
        }

        return "User " + userName + " not found";
    }

    @Override
    public void updateUserHistory() {
        USER_EXECUTOR.execute(() -> {
            for (String updatedUser : UserStatsRepository.instance.updateUserStats()) {
                getUserByName(updatedUser).ifPresent(User::resetUserStats);
            }
        });
    }

    @Override
    public void checkHealth() {
        //logger.info("Checking users...");
        // TODO: add broken users check and report (too long without sessions)
    }
}
