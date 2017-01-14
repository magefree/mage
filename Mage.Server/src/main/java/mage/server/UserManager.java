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

import mage.server.User.UserState;
import mage.server.record.UserStats;
import mage.server.record.UserStatsRepository;
import mage.server.util.ThreadExecutor;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.*;

/**
 * manages users - if a user is disconnected and 10 minutes have passed with no
 * activity the user is removed
 *
 * @author BetaSteward_at_googlemail.com
 */
public class UserManager {

    protected static final ScheduledExecutorService expireExecutor = Executors.newSingleThreadScheduledExecutor();

    private static final Logger LOGGER = Logger.getLogger(UserManager.class);

    private final ConcurrentHashMap<UUID, User> users = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, User> usersByName = new ConcurrentHashMap<>();

    private static final ExecutorService USER_EXECUTOR = ThreadExecutor.getInstance().getCallExecutor();

    private static final UserManager INSTANCE = new UserManager();

    public static UserManager getInstance() {
        return INSTANCE;
    }

    private UserManager() {
        expireExecutor.scheduleAtFixedRate(() -> checkExpired(), 60, 60, TimeUnit.SECONDS);
    }

    public User createUser(String userName, String host, AuthorizedUser authorizedUser) {
        if (getUserByName(userName) != null) {
            return null; //user already exists
        }
        User user = new User(userName, host, authorizedUser);
        users.put(user.getId(), user);
        usersByName.put(userName, user);
        return user;
    }

    public Optional<User> getUser(UUID userId) {
        if (users.get(userId) == null) {
            LOGGER.error(String.format("User with id %s could not be found", userId));
            return Optional.empty();
        } else {
            return Optional.of(users.get(userId));
        }
      /*  if (userId != null) {
            return users.get(userId);
        }
        return null;
        */
    }

    public User getUserByName(String userName) {
        return usersByName.get(userName);
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
        if (userId != null) {
            User user = users.get(userId);
            if (user != null) {
                user.setSessionId(""); // Session will be set again with new id if user reconnects
            }
            ChatManager.getInstance().removeUser(userId, reason);
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

    public void removeUser(final UUID userId, final DisconnectReason reason) {
        if (userId != null) {
            final User user = users.get(userId);
            if (user != null) {
                USER_EXECUTOR.execute(
                        () -> {
                            try {
                                LOGGER.info("USER REMOVE - " + user.getName() + " (" + reason.toString() + ")  userId: " + userId + " [" + user.getGameInfo() + "]");
                                user.remove(reason);
                                LOGGER.debug("USER REMOVE END - " + user.getName());
                            } catch (Exception ex) {
                                handleException(ex);
                            } finally {
                                users.remove(userId);
                                usersByName.remove(user.getName());
                            }
                        }
                );
            } else {
                LOGGER.warn("Trying to remove userId: " + userId + " - but it does not exist.");
            }
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
     * Is the connection lost for more than 3 minutes, the user will be removed
     * (within 3 minutes the user can reconnect)
     */
    private void checkExpired() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -3);
        List<User> usersToCheck = new ArrayList<>();
        usersToCheck.addAll(users.values());
        for (User user : usersToCheck) {
            if (!user.getUserState().equals(UserState.Expired) && user.isExpired(calendar.getTime())) {
                removeUser(user.getId(), DisconnectReason.SessionExpired);
            }
        }
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
        User user = getUserByName(userName);
        if (user != null) {
            return "History of user " + userName + " - " + user.getUserData().getHistory();
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
                User user = getUserByName(updatedUser);
                if (user != null) {
                    user.resetUserStats();
                }
            }
        });
    }
}
