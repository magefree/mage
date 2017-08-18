/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.server;

import java.util.*;
import java.util.concurrent.*;
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
        users.put(user.getId(), user);
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
        Optional<User> u = users.values().stream().filter(user -> user.getName().equals(userName))
                .findFirst();
        if (u.isPresent()) {
            return u;
        } else {
            return Optional.empty();
        }
    }

    public Collection<User> getUsers() {
        return users.values();
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
                removeUserFromAllTables(userId, reason);
                user.get().setUserState(UserState.Offline);
            }
        }
        if (userId != null) {
            ChatManager.instance.removeUser(userId, reason);
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

    public void removeUserFromAllTables(final UUID userId, final DisconnectReason reason) {
        if (userId != null) {
            getUser(userId).ifPresent(user
                    -> USER_EXECUTOR.execute(
                            () -> {
                                try {
                                    LOGGER.info("USER REMOVE - " + user.getName() + " (" + reason.toString() + ")  userId: " + userId + " [" + user.getGameInfo() + ']');
                                    user.removeUserFromAllTables(reason);
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
            for (User user : users.values()) {
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
                            removeUserFromAllTables(user.getId(), DisconnectReason.SessionExpired);
                            user.setUserState(UserState.Offline);
                            // Remove the user from all tournaments

                        }
                    }
                } catch (Exception ex) {
                    handleException(ex);
                }
            }
            for (User user : toRemove) {
                users.remove(user.getId());
            }
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
