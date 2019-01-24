
package mage.server;

import mage.MageException;
import mage.players.net.UserData;
import org.apache.log4j.Logger;
import org.jboss.remoting.callback.InvokerCallbackHandler;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

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
        if (session.getUserId() != null && !UserManager.instance.getUser(session.getUserId()).isPresent()) {
            logger.error("User for session " + sessionId + " with userId " + session.getUserId() + " is missing. Session removed.");
            // can happen if user from same host signs in multiple time with multiple clients, after he disconnects with one client
            disconnect(sessionId, DisconnectReason.ConnectingOtherInstance, session); // direct disconnect
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
      return getSession(sessionId)
              .map(session -> session.setUserData(userName,userData, clientVersion, userIdStr))
              .orElse(false);

    }

    public void disconnect(String sessionId, DisconnectReason reason) {
        disconnect(sessionId, reason, null);
    }

    public void disconnect(String sessionId, DisconnectReason reason, Session directSession) {
        if (directSession == null) {
            // find real session to disconnects
            getSession(sessionId).ifPresent(session -> {
                if (!isValidSession(sessionId)) {
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
            });
        } else {
            // direct session to disconnects
            sessions.remove(sessionId);
            directSession.kill(reason);
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
        return getSession(sessionId)
                .flatMap(s -> UserManager.instance.getUser(s.getUserId()));

    }

    public void endUserSession(String sessionId, String userSessionId) {
        if (isAdmin(sessionId)) {
            disconnect(userSessionId, DisconnectReason.AdminDisconnect);
        }
    }

    public boolean isAdmin(String sessionId) {
        return getSession(sessionId).map(Session::isAdmin).orElse(false);

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
        return getSession(sessionId)
                .map(session -> UserManager.instance.extendUserSession(session.getUserId(), pingInfo))
                .orElse(false);
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
