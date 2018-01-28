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

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nonnull;
import mage.MageException;
import mage.players.net.UserData;
import org.apache.log4j.Logger;
import org.jboss.remoting.callback.InvokerCallbackHandler;

/**
 * @author BetaSteward_at_googlemail.com
 */
public enum SessionManager {

    instance;

    private static final Logger logger = Logger.getLogger(SessionManager.class);

    private final ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();

    public Optional<Session> getSession(@Nonnull String sessionId) {
        Session session = sessions.get(sessionId);
        if (session == null) {
            logger.trace("Session with sessionId " + sessionId + " is not found");
            return Optional.empty();
        }
        if (session.getUserId() != null && UserManager.instance.getUser(session.getUserId()) == null) {
            logger.error("User for session " + sessionId + " with userId " + session.getUserId() + " is missing. Session removed.");
            // can happen if user from same host signs in multiple time with multiple clients, after he disconnects with one client
            disconnect(sessionId, DisconnectReason.ConnectingOtherInstance);
            return Optional.empty();
        }
        return Optional.of(session);
    }

    public void createSession(String sessionId, InvokerCallbackHandler callbackHandler) {
        Session session = new Session(sessionId, callbackHandler);
        sessions.put(sessionId, session);
    }

    public boolean registerUser(String sessionId, String userName, String password, String email) throws MageException {
        Session session = sessions.get(sessionId);
        if (session == null) {
            logger.error(userName + " tried to register with no sessionId");
            return false;
        }
        String returnMessage = session.registerUser(userName, password, email);
        if (returnMessage != null) {
            logger.debug(userName + " not registered: " + returnMessage);
            return false;
        }
        logger.info(userName + " registered");
        logger.debug("- userId:    " + session.getUserId());
        logger.debug("- sessionId: " + sessionId);
        logger.debug("- host:      " + session.getHost());
        return true;
    }

    public boolean connectUser(String sessionId, String userName, String password, String userIdStr) throws MageException {
        Session session = sessions.get(sessionId);
        if (session != null) {
            String returnMessage = session.connectUser(userName, password);
            if (returnMessage == null) {
                logger.info(userName + " connected to server");
                logger.debug("- userId:    " + session.getUserId());
                logger.debug("- sessionId: " + sessionId);
                logger.debug("- host:      " + session.getHost());
                return true;
            } else {
                logger.debug(userName + " not connected: " + returnMessage);
            }
        } else {
            logger.error(userName + " tried to connect with no sessionId");
        }
        return false;
    }

    public boolean connectAdmin(String sessionId) {
        Session session = sessions.get(sessionId);
        if (session != null) {
            session.connectAdmin();
            logger.info("Admin connected from " + session.getHost());
            return true;
        }
        return false;
    }

    public boolean setUserData(String userName, String sessionId, UserData userData, String clientVersion, String userIdStr) throws MageException {
        Session session = sessions.get(sessionId);
        if (session != null) {
            session.setUserData(userName, userData, clientVersion, userIdStr);
            return true;
        }
        return false;
    }

    public void disconnect(String sessionId, DisconnectReason reason) {
        Session session = sessions.get(sessionId);
        if (session != null) {
            if (!sessions.containsKey(sessionId)) {
                // session was removed meanwhile by another thread so we can return
                return;
            }
            logger.debug("DISCONNECT  " + reason.toString() + " - sessionId: " + sessionId);
            sessions.remove(sessionId);
            switch (reason) {
                case AdminDisconnect:
                    session.kill(reason);
                    break;
                case ConnectingOtherInstance:
                case Disconnected: // regular session end or wrong client version
                    UserManager.instance.disconnect(session.getUserId(), reason);
                    break;
                case SessionExpired: // session ends after no reconnect happens in the defined time span
                    break;
                case LostConnection: // user lost connection - session expires countdown starts
                    session.userLostConnection();
                    UserManager.instance.disconnect(session.getUserId(), reason);
                    break;
                default:
                    logger.trace("endSession: unexpected reason  " + reason.toString() + " - sessionId: " + sessionId);
            }

        }

    }

    /**
     * Admin requested the disconnect of a user
     *
     * @param sessionId
     * @param userSessionId
     */
    public void disconnectUser(String sessionId, String userSessionId) {
        if (isAdmin(sessionId)) {
            getUserFromSession(sessionId).ifPresent(admin -> {
                Optional<User> u = getUserFromSession(userSessionId);
                if (u.isPresent()) {
                    User user = u.get();
                    user.showUserMessage("Admin operation", "Your session was disconnected by Admin.");
                    admin.showUserMessage("Admin action", "User" + user.getName() + " was disconnected.");
                    disconnect(userSessionId, DisconnectReason.AdminDisconnect);
                } else {
                    admin.showUserMessage("Admin operation", "User with sessionId " + userSessionId + " could not be found!");
                }
            });
        }
    }

    private Optional<User> getUserFromSession(String sessionId) {
        Optional<Session> session = getSession(sessionId);
        if (!session.isPresent()) {
            return Optional.empty();
        }
        return UserManager.instance.getUser(session.get().getUserId());
    }

    public void endUserSession(String sessionId, String userSessionId) {
        if (isAdmin(sessionId)) {
            disconnect(userSessionId, DisconnectReason.AdminDisconnect);
        }
    }

    public boolean isAdmin(String sessionId) {
        Session admin = sessions.get(sessionId);
        if (admin != null) {
            return admin.isAdmin();
        }
        return false;
    }

    public boolean isValidSession(@Nonnull String sessionId) {
        return sessions.containsKey(sessionId);
    }

    public Optional<User> getUser(@Nonnull String sessionId) {
        Session session = sessions.get(sessionId);
        if (session != null) {
            return UserManager.instance.getUser(sessions.get(sessionId).getUserId());
        }
        logger.error(String.format("Session %s could not be found", sessionId));
        return Optional.empty();
    }

    public boolean extendUserSession(String sessionId, String pingInfo) {
        Session session = sessions.get(sessionId);
        if (session != null) {
            return UserManager.instance.extendUserSession(session.getUserId(), pingInfo);
        }
        return false;
    }

    public void sendErrorMessageToClient(String sessionId, String message) {
        Session session = sessions.get(sessionId);
        if (session == null) {
            logger.error("Following error message is not delivered because session " + sessionId + " is not found: " + message);
            return;
        }
        session.sendErrorMessageToClient(message);
    }
}
