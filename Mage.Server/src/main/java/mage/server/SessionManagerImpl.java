package mage.server;

import mage.MageException;
import mage.players.net.UserData;
import mage.server.managers.SessionManager;
import mage.server.managers.ManagerFactory;
import org.apache.log4j.Logger;
import org.jboss.remoting.callback.InvokerCallbackHandler;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class SessionManagerImpl implements SessionManager {

    private static final Logger logger = Logger.getLogger(SessionManagerImpl.class);

    private final ManagerFactory managerFactory;
    private final ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();

    public SessionManagerImpl(ManagerFactory managerFactory) {
        this.managerFactory = managerFactory;
    }

    @Override
    public Optional<Session> getSession(@Nonnull String sessionId) {
        Session session = sessions.get(sessionId);
        if (session == null) {
            logger.trace("Session with sessionId " + sessionId + " is not found");
            return Optional.empty();
        }
        if (session.getUserId() != null && !managerFactory.userManager().getUser(session.getUserId()).isPresent()) {
            logger.error("User for session " + sessionId + " with userId " + session.getUserId() + " is missing. Session removed.");
            // can happen if user from same host signs in multiple time with multiple clients, after they disconnect with one client
            disconnect(sessionId, DisconnectReason.ConnectingOtherInstance, session); // direct disconnect
            return Optional.empty();
        }
        return Optional.of(session);
    }

    @Override
    public void createSession(String sessionId, InvokerCallbackHandler callbackHandler) {
        Session session = new Session(managerFactory, sessionId, callbackHandler);
        sessions.put(sessionId, session);
    }

    @Override
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

    @Override
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

    @Override
    public boolean connectAdmin(String sessionId) {
        Session session = sessions.get(sessionId);
        if (session != null) {
            session.connectAdmin();
            logger.warn("Admin connected from " + session.getHost());
            return true;
        }
        return false;
    }

    @Override
    public boolean setUserData(String userName, String sessionId, UserData userData, String clientVersion, String userIdStr) throws MageException {
        return getSession(sessionId)
                .map(session -> session.setUserData(userName, userData, clientVersion, userIdStr))
                .orElse(false);

    }

    @Override
    public void disconnect(String sessionId, DisconnectReason reason) {
        disconnect(sessionId, reason, null);
    }

    @Override
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
                        managerFactory.userManager().disconnect(session.getUserId(), reason);
                        break;
                    case SessionExpired: // session ends after no reconnect happens in the defined time span
                        break;
                    case LostConnection: // user lost connection - session expires countdown starts
                        session.userLostConnection();
                        managerFactory.userManager().disconnect(session.getUserId(), reason);
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
    @Override
    public void disconnectUser(String sessionId, String userSessionId) {
        if (!checkAdminAccess(sessionId)) {
            return;
        }
        getUserFromSession(sessionId).ifPresent(admin -> {
            Optional<User> u = getUserFromSession(userSessionId);
            if (u.isPresent()) {
                User user = u.get();
                user.showUserMessage("Admin action", "Your session was disconnected by admin");
                admin.showUserMessage("Admin result", "User " + user.getName() + " was disconnected");
                disconnect(userSessionId, DisconnectReason.AdminDisconnect);
            } else {
                admin.showUserMessage("Admin result", "User with sessionId " + userSessionId + " could not be found");
            }
        });
    }

    private Optional<User> getUserFromSession(String sessionId) {
        return getSession(sessionId)
                .flatMap(s -> managerFactory.userManager().getUser(s.getUserId()));

    }

    @Override
    public void endUserSession(String sessionId, String userSessionId) {
        if (!checkAdminAccess(sessionId)) {
            return;
        }

        disconnect(userSessionId, DisconnectReason.AdminDisconnect);
    }

    @Override
    public boolean checkAdminAccess(String sessionId) {
        Session session = sessions.get(sessionId);
        if (session == null) {
            logger.error("Wrong admin access with unknown session: " + sessionId, new Throwable());
        } else if (!session.isAdmin()) {
            String info = String.format("sessionId %s for userId %s at %s",
                    session.getId(),
                    session.getUserId(),
                    session.getHost()
            );
            logger.error("Wrong admin access with user session: " + info, new Throwable());
        }
        return session != null && session.isAdmin();
    }

    @Override
    public boolean isValidSession(@Nonnull String sessionId) {
        return sessions.containsKey(sessionId);
    }

    @Override
    public Optional<User> getUser(@Nonnull String sessionId) {
        Session session = sessions.get(sessionId);
        if (session != null) {
            return managerFactory.userManager().getUser(sessions.get(sessionId).getUserId());
        }
        logger.error(String.format("Session %s could not be found", sessionId));
        return Optional.empty();
    }

    @Override
    public boolean extendUserSession(String sessionId, String pingInfo) {
        return getSession(sessionId)
                .map(session -> managerFactory.userManager().extendUserSession(session.getUserId(), pingInfo))
                .orElse(false);
    }

    @Override
    public void sendErrorMessageToClient(String sessionId, String message) {
        Session session = sessions.get(sessionId);
        if (session == null) {
            logger.error("Following error message is not delivered because session " + sessionId + " is not found: " + message);
            return;
        }
        session.sendErrorMessageToClient(message);
    }
}
