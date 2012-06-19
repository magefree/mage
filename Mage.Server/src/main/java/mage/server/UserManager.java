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

import mage.view.ChatMessage.MessageColor;
import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * manages users - if a user is disconnected and 10 minutes have passed with no
 * activity the user is removed
 * 
 * @author BetaSteward_at_googlemail.com
 */
public class UserManager {

    protected static ScheduledExecutorService expireExecutor = Executors.newSingleThreadScheduledExecutor();

    private final static UserManager INSTANCE = new UserManager();
    private final static Logger logger = Logger.getLogger(UserManager.class);

    public static UserManager getInstance() {
        return INSTANCE;
    }

    private UserManager() {
        expireExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                checkExpired();
            }
        }, 60, 60, TimeUnit.SECONDS);
    }

    private ConcurrentHashMap<UUID, User> users = new ConcurrentHashMap<UUID, User>();

    public User createUser(String userName, String host) {
        if (findUser(userName) != null)
            return null; //user already exists
        User user = new User(userName, host);
        users.put(user.getId(), user);
        return user;
    }

    public User getUser(UUID userId) {
        return users.get(userId);
    }

    public User findUser(String userName) {
        for (User user: users.values()) {
            if (user.getName().equals(userName))
                return user;
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

    public void disconnect(UUID userId) {
        if (userId != null) {
            ChatManager.getInstance().removeUser(userId);
            if (users.containsKey(userId)) {
                logger.info("user disconnected " + userId);
                users.get(userId).setSessionId("");
                ChatManager.getInstance().broadcast(userId, "has lost connection", MessageColor.BLACK);
            }
        }
    }

    public boolean isAdmin(UUID userId) {
        if (users.containsKey(userId)) {
            return users.get(userId).getName().equals("Admin");
        }
        return false;
    }

    public void removeUser(UUID userId) {
        if (users.containsKey(userId)) {
            logger.info("user removed" + userId);
            users.get(userId).setSessionId("");
            ChatManager.getInstance().broadcast(userId, "has disconnected", MessageColor.BLACK);
            users.get(userId).kill();
            users.remove(userId);
        }
    }

    public boolean extendUserSession(UUID userId) {
        if (users.containsKey(userId)) {
            users.get(userId).updateLastActivity();
            return true;
        }
        return false;
    }

    private void checkExpired() {
        Calendar expired = Calendar.getInstance();
        expired.add(Calendar.MINUTE, -3) ;
        for (User user: users.values()) {
            if (user.isExpired(expired.getTime())) {
                logger.info(user.getName() + " session expired " + user.getId());
                user.kill();
                users.remove(user.getId());
            }
        }
    }

}
