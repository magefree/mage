
package mage.server;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import mage.server.User.UserState;
import mage.server.record.UserStats;
import mage.server.record.UserStatsRepository;
import mage.server.util.ThreadExecutor;
import mage.view.UserView;
import org.apache.log4j.Logger;

/**
 * manages users - if a user is disconnected and 10 minutes have passed with no
 * activity the user is removed
 *
 * @author BetaSteward_at_googlemail.com
 */
public enum UserManager {
    instance;

    private static final Logger logger = Logger.getLogger(UserManager.class);

    protected final ScheduledExecutorService expireExecutor = Executors.newSingleThreadScheduledExecutor();
    protected final ScheduledExecutorService userListExecutor = Executors.newSingleThreadScheduledExecutor();

    private List<UserView> userInfoList = new ArrayList<>();

    private static final Logger LOGGER = Logger.getLogger(UserManager.class);

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final ConcurrentHashMap<UUID, User> users = new ConcurrentHashMap<>();

    private static final ExecutorService USER_EXECUTOR = ThreadExecutor.instance.getCallExecutor();

    UserManager() {
        expireExecutor.scheduleAtFixedRate(this::checkExpired, 60, 60, TimeUnit.SECONDS);

        userListExecutor.scheduleAtFixedRate(this::updateUserInfoList, 4, 4, TimeUnit.SECONDS);
    }

    public Optional<User> createUser(String userName, String host, AuthorizedUser authorizedUser) {
        if (getUserByName(userName).isPresent()) {
            return Optional.empty(); //user already exists
        }
        User user = new User(userName, host, authorizedUser);
        final Lock w = lock.writeLock();
        w.lock();
        try {
            users.put(user.getId(), user);
        } finally {
            w.unlock();
        }
        return Optional.of(user);
    }

    public Optional<User> getUser(UUID userId) {
        if (!users.containsKey(userId)) {
            LOGGER.trace(String.format("User with id %s could not be found", userId));
            return Optional.empty();
        } else {
            return Optional.of(users.get(userId));
        }
    }

    public Optional<User> getUserByName(String userName) {
        final Lock r = lock.readLock();
        r.lock();
        try {
            Optional<User> u = users.values().stream().filter(user -> user.getName().equals(userName))
                    .findFirst();
            if (u.isPresent()) {
                return u;
            } else {
                return Optional.empty();
            }
        } finally {
            r.unlock();
        }

    }

    public Collection<User> getUsers() {
        ArrayList<User> userList = new ArrayList<>();
        final Lock r = lock.readLock();
        r.lock();
        try {
            userList.addAll(users.values());
        } finally {
            r.unlock();
        }
        return userList;
    }

    public boolean connectToSession(String sessionId, UUID userId) {
        if (userId != null) {
            User user = users.get(userId);
            if (user != null) {
                user.setSessionId(sessionId);
                return true;
            }
        }
        return false;
    }

    public void disconnect(UUID userId, DisconnectReason reason) {
        Optional<User> user = UserManager.instance.getUser(userId);
        if (user.isPresent()) {
            user.get().setSessionId("");
            if (reason == DisconnectReason.Disconnected) {
                removeUserFromAllTablesAndChat(userId, reason);
                user.get().setUserState(UserState.Offline);
            } else {
//                ChatManager.instance.sendLostConnectionMessage(userId, reason);
            }
        }
    }

    public boolean isAdmin(UUID userId) {
        if (userId != null) {
            User user = users.get(userId);
            if (user != null) {
                return user.getName().equals("Admin");
            }
        }
        return false;
    }

    public void removeUserFromAllTablesAndChat(final UUID userId, final DisconnectReason reason) {
        if (userId != null) {
            getUser(userId).ifPresent(user
                    -> USER_EXECUTOR.execute(
                            () -> {
                                try {
                                    LOGGER.info("USER REMOVE - " + user.getName() + " (" + reason.toString() + ")  userId: " + userId + " [" + user.getGameInfo() + ']');
                                    user.removeUserFromAllTables(reason);
                                    ChatManager.instance.removeUser(user.getId(), reason);
                                    LOGGER.debug("USER REMOVE END - " + user.getName());
                                } catch (Exception ex) {
                                    handleException(ex);
                                }
                            }
                    ));

        }
    }

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
     * offline status. The user will be removed in validity check after 15
     * minutes of no activities
     *
     */
    private void checkExpired() {
        try {
            Calendar calendarExp = Calendar.getInstance();
            calendarExp.add(Calendar.MINUTE, -3);
            Calendar calendarRemove = Calendar.getInstance();
            calendarRemove.add(Calendar.MINUTE, -8);
            List<User> toRemove = new ArrayList<>();
            logger.debug("Start Check Expired");
            ArrayList<User> userList = new ArrayList<>();
            final Lock r = lock.readLock();
            r.lock();
            try {
                userList.addAll(users.values());
            } finally {
                r.unlock();
            }
            for (User user : userList) {
                try {
                    if (user.getUserState() == UserState.Offline) {
                        if (user.isExpired(calendarRemove.getTime())) {
                            toRemove.add(user);
                        }
                    } else {
                        if (user.isExpired(calendarExp.getTime())) {
                            if (user.getUserState() == UserState.Connected) {
                                user.lostConnection();
                                disconnect(user.getId(), DisconnectReason.BecameInactive);
                            }
                            removeUserFromAllTablesAndChat(user.getId(), DisconnectReason.SessionExpired);
                            user.setUserState(UserState.Offline);
                            // Remove the user from all tournaments

                        }
                    }
                } catch (Exception ex) {
                    handleException(ex);
                }
            }
            logger.debug("Users to remove " + toRemove.size());
            final Lock w = lock.readLock();
            w.lock();
            try {
                for (User user : toRemove) {
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
     * This method recreated the user list that will be send to all clients
     *
     */
    private void updateUserInfoList() {
        try {
            List<UserView> newUserInfoList = new ArrayList<>();
            for (User user : UserManager.instance.getUsers()) {
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
            }
            userInfoList = newUserInfoList;
        } catch (Exception ex) {
            handleException(ex);
        }
    }

    public List<UserView> getUserInfoList() {
        return userInfoList;
    }

    public void handleException(Exception ex) {
        if (ex != null) {
            LOGGER.fatal("User manager exception ", ex);
            if (ex.getStackTrace() != null) {
                LOGGER.fatal(ex.getStackTrace());
            }
        } else {
            LOGGER.fatal("User manager exception - null");
        }
    }

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

    public void updateUserHistory() {
        USER_EXECUTOR.execute(() -> {
            for (String updatedUser : UserStatsRepository.instance.updateUserStats()) {
                getUserByName(updatedUser).ifPresent(User::resetUserStats);
            }
        });
    }
}
