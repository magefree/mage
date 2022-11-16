package mage.server;

import mage.server.User.UserState;
import mage.server.managers.UserManager;
import mage.server.managers.ManagerFactory;
import mage.server.record.UserStats;
import mage.server.record.UserStatsRepository;
import mage.view.UserView;
import mage.remote.DisconnectReason;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * manages users - if a user is disconnected and 10 minutes have passed with no
 * activity the user is removed
 *
 * @author BetaSteward_at_googlemail.com
 */
public class UserManagerImpl implements UserManager {

    private static final int SERVER_TIMEOUTS_USER_INFORM_OPPONENTS_ABOUT_DISCONNECT_AFTER_SECS = 30; // send to chat info about disconnection troubles, must be more than ping timeout
    private static final int SERVER_TIMEOUTS_USER_DISCONNECT_FROM_SERVER_AFTER_SECS = 3 * 60; // removes from all games and chats too (can be seen in users list with disconnected status)
    private static final int SERVER_TIMEOUTS_USER_REMOVE_FROM_SERVER_AFTER_SECS = 8 * 60; // removes from users list

    private static final Logger logger = Logger.getLogger(UserManagerImpl.class);

    protected final ScheduledExecutorService expireExecutor = Executors.newSingleThreadScheduledExecutor();
    protected final ScheduledExecutorService userListExecutor = Executors.newSingleThreadScheduledExecutor();

    private List<UserView> userInfoList = new ArrayList<>();
    private final ManagerFactory managerFactory;


    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final ConcurrentHashMap<UUID, User> users = new ConcurrentHashMap<>();

    private ExecutorService USER_EXECUTOR;

    public UserManagerImpl(ManagerFactory managerFactory) {
        this.managerFactory = managerFactory;
    }

    public void init() {
        USER_EXECUTOR = managerFactory.threadExecutor().getCallExecutor();
        
        userListExecutor.scheduleAtFixedRate(this::updateUserInfoList, 4, 4, TimeUnit.SECONDS);
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
        if (!users.containsKey(userId)) {
            //logger.warn(String.format("User with id %s could not be found", userId), new Throwable()); // TODO: remove after session freezes fixed
            return Optional.empty();
        } else {
            return Optional.of(users.get(userId));
        }
    }

    @Override
    public Optional<User> getUserByName(String userName) {
        final Lock r = lock.readLock();
        r.lock();
        try {
            return users.values().stream().filter(user -> user.getName().equals(userName))
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
            User user = users.get(userId);
            if (user != null) {
                user.setSessionId(sessionId);
                return true;
            }
        }
        return false;
    }

    @Override
    public void disconnect(UUID userId, DisconnectReason reason) {
        Optional<User> user = getUser(userId);
        if (user.isPresent()) {
            user.get().setSessionId("");
            if (reason == DisconnectReason.Disconnected) {
                removeUserFromAllTablesAndChat(userId, reason);
                user.get().setUserState(UserState.Offline);
            }
        }
    }

    @Override
    public boolean isAdmin(UUID userId) {
        if (userId != null) {
            User user = users.get(userId);
            if (user != null) {
                return user.getName().equals("Admin");
            }
        }
        return false;
    }

    @Override
    public void removeUserFromAllTablesAndChat(final UUID userId, final DisconnectReason reason) {
        if (userId != null) {
            getUser(userId).ifPresent(user
                    -> USER_EXECUTOR.execute(
                    () -> {
                        try {
                            logger.info("USER REMOVE - " + user.getName() + " (" + reason.toString() + ")  userId: " + userId + " [" + user.getGameInfo() + ']');
                            user.removeUserFromAllTables(reason);
                            managerFactory.chatManager().removeUser(user.getId(), reason);
                            logger.debug("USER REMOVE END - " + user.getName());
                        } catch (Exception ex) {
                            handleException(ex);
                        }
                    }
            ));

        }
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
     * This method recreated the user list that will be send to all clients
     */
    private void updateUserInfoList() {
        try {
            List<UserView> newUserInfoList = new ArrayList<>();
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
            }
            userInfoList = newUserInfoList;
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
}
