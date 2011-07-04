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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import mage.MageException;
import mage.view.UserView;
import org.apache.log4j.Logger;
import org.jboss.remoting.callback.InvokerCallbackHandler;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SessionManager {

	private final static Logger logger = Logger.getLogger(SessionManager.class);
	private final static SessionManager INSTANCE = new SessionManager();

	public static SessionManager getInstance() {
		return INSTANCE;
	}

	private ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<String, Session>();

	public Session getSession(String sessionId) {
		if (sessions == null || sessionId == null) return null;
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
			logger.info("User " + userName + " connected from " + session.getHost());
			return true;
		}
		return false;
	}
	
	public boolean registerAdmin(String sessionId) {
		Session session = sessions.get(sessionId);
		if (session != null) {
			session.registerAdmin();
			logger.info("Admin connected from " + session.getHost());
			return true;
		}
		return false;
	}
	
	public synchronized void disconnect(String sessionId) {
		Session session = sessions.get(sessionId);
		if (session != null) {
			session.kill();
			sessions.remove(sessionId);
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
			Session session = sessions.get(userSessionId);
			if (session != null) {
				session.kill();
			}
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
		if (sessions.containsKey(sessionId))
			return true;
		return false;
	}

}
