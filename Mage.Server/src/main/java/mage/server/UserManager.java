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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import mage.view.ChatMessage.MessageColor;
import org.apache.log4j.Logger;

/**
 *
 * manages users - if a user is disconnected and 10 minutes have passed with no
 * activity the user is removed
 * 
 * @author BetaSteward_at_googlemail.com
 */
public class UserManager {

    protected static ScheduledExecutorService expireExecutor = Executors.newSingleThreadScheduledExecutor();

    private static final UserManager INSTANCE = new UserManager();
    private static final Logger logger = Logger.getLogger(UserManager.class);

    public static UserManager getInstance() {
        return INSTANCE;
    }

    private UserManager() {
        expireExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                logger.debug("Check expired start");
                checkExpired();
                logger.debug("Check expired end");
            }
        }, 60, 60, TimeUnit.SECONDS);
    }

    private final ConcurrentHashMap<UUID, User> users = new ConcurrentHashMap<>();

    public User createUser(String userName, String host) {
        if (findUser(userName) != null) {
            return null; //user already exists
        }
        User user = new User(userName, host);
        users.put(user.getId(), user);
        return user;
    }

    public User getUser(UUID userId) {
        return users.get(userId);
    }

    public User findUser(String userName) {
        for (User user: users.values()) {
            if (user.getName().equals(userName)) {
                return user;
            }
        }
        return null;
    }

    public Collection<User> getUsers() {
        return users.values();
    }

    public boolean connectToSession(String sessionId, UUID userId) {
        if (users.containsKey(userId)) {
            users.get(userId).setSessionId(sessionId);
                return true;
            }
        return false;
    }

    public void disconnect(UUID userId, User.DisconnectReason reason) {
        if (userId != null) {
            if (users.containsKey(userId)) {
                User user = users.get(userId);
                user.setSessionId(""); // Session will be set again with new id if user reconnects
                ChatManager.getInstance().broadcast(userId, "has lost connection", MessageColor.BLACK);
                logger.info(new StringBuilder("User ").append(user.getName()).append(" has lost connection  userId:").append(userId));                
            }
            ChatManager.getInstance().removeUser(userId, reason);
        }
    }

    public boolean isAdmin(UUID userId) {
        if (users.containsKey(userId)) {
            return users.get(userId).getName().equals("Admin");
        }
        return false;
    }

    public void removeUser(UUID userId, User.DisconnectReason reason) {
        User user = users.get(userId);
        if (user != null) {
            logger.info(new StringBuilder("Remove user: ").append(user.getName())
                    .append(" userId: ").append(userId)
                    .append(" sessionId: ").append(user.getSessionId())
                    .append(" Reason: ").append(reason.toString()));
            ChatManager.getInstance().removeUser(userId, reason);
            ChatManager.getInstance().broadcast(userId, new StringBuilder("has disconnected (").append(reason.toString()).append(")").toString(), MessageColor.BLACK);
            users.get(userId).kill(reason);
            users.remove(userId);
        } else {
            logger.warn(new StringBuilder("Trying to remove userId: ").append(userId).append(" but user does not exist."));
        }
    }

    public boolean extendUserSession(UUID userId) {
        if (users.containsKey(userId)) {
            users.get(userId).updateLastActivity();
            return true;
        }
        return false;
    }

    /**
     * Is the connection lost for more than 3 minutes, the user will be removed (within 3 minutes he can reconnect)
     */
    private void checkExpired() {
        Calendar expired = Calendar.getInstance();
        expired.add(Calendar.MINUTE, -3) ;
        List<User> usersToCheck = new ArrayList<>();
        usersToCheck.addAll(users.values());
        for (User user: usersToCheck) {
            if (user.isExpired(expired.getTime())) {
                logger.info(new StringBuilder(user.getName()).append(" session expired userId: ").append(user.getId())
                        .append(" sessionId: ").append(user.getSessionId()));
                user.kill(User.DisconnectReason.LostConnection);
                users.remove(user.getId());
            }
        }
    }

}
