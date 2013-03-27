/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.server;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import mage.MageException;
import mage.server.services.LogKeys;
import mage.server.services.impl.LogServiceImpl;
import mage.view.UserDataView;
import org.apache.log4j.Logger;
import org.jboss.remoting.callback.InvokerCallbackHandler;


/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SessionManager {

    private static final Logger logger = Logger.getLogger(SessionManager.class);
    private static final SessionManager INSTANCE = new SessionManager();

    public static SessionManager getInstance() {
        return INSTANCE;
    }

    private ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<String, Session>();

    public Session getSession(String sessionId) {
        if (sessions == null || sessionId == null) {
            return null;
        }
        return sessions.get(sessionId);
    }

    public void createSession(String sessionId, InvokerCallbackHandler callbackHandler) {
        Session session = new Session(sessionId, callbackHandler);
        sessions.put(sessionId, session);
    }

    public boolean registerUser(String sessionId, String userName) throws MageException {
        Session session = sessions.get(sessionId);
        if (session != null) {
            session.registerUser(userName);
            LogServiceImpl.instance.log(LogKeys.KEY_USER_CONNECTED, userName, session.getHost(), sessionId);
            logger.info("User " + userName + " connected from " + session.getHost() + " sessionId: " + sessionId);
            return true;
        }
        return false;
    }

    public boolean registerAdmin(String sessionId) {
        Session session = sessions.get(sessionId);
        if (session != null) {
            session.registerAdmin();
            LogServiceImpl.instance.log(LogKeys.KEY_ADMIN_CONNECTED, "Admin", session.getHost(), sessionId);
            logger.info("Admin connected from " + session.getHost());
            return true;
        }
        return false;
    }

    public boolean setUserData(String userName, String sessionId, UserDataView userDataView) throws MageException {
        Session session = sessions.get(sessionId);
        if (session != null) {
            session.setUserData(userName, userDataView);
            return true;
        }
        return false;
    }

    public synchronized void disconnect(String sessionId, boolean voluntary) {
        Session session = sessions.get(sessionId);
           sessions.remove(sessionId);
        if (session != null) {
            if (voluntary) {
                session.kill();
                LogServiceImpl.instance.log(LogKeys.KEY_SESSION_KILLED, sessionId);
            } else {
                session.disconnect();
                LogServiceImpl.instance.log(LogKeys.KEY_SESSION_DISCONNECTED, sessionId);
            }
        } else {
            logger.info("could not find session with id " + sessionId);
        }
    }

    public Map<String, Session> getSessions() {
        Map<String, Session> map = new HashMap<String, Session>();
        for (Map.Entry<String, Session> entry : sessions.entrySet()) {
             map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    public void disconnectUser(String sessionId, String userSessionId) {
        if (isAdmin(sessionId)) {
            disconnect(userSessionId, true);
            LogServiceImpl.instance.log(LogKeys.KEY_SESSION_DISCONNECTED_BY_ADMIN, sessionId, userSessionId);
        }
    }

    public boolean isAdmin(String sessionId) {
        Session admin = sessions.get(sessionId);
        if (admin != null) {
            return admin.isAdmin();
        }
        return false;
    }

    public boolean isValidSession(String sessionId) {
        if (sessions.containsKey(sessionId)) {
            return true;
        }
        return false;
    }

    public User getUser(String sessionId) {
        if (sessions.containsKey(sessionId)) {
            return UserManager.getInstance().getUser(sessions.get(sessionId).getUserId());
        }
        return null;
    }

    public boolean extendUserSession(String sessionId) {
        if (sessions.containsKey(sessionId)) {
            return UserManager.getInstance().extendUserSession(sessions.get(sessionId).getUserId());
        }
        return false;
    }
}
