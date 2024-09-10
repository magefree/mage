package mage.server;

import mage.MageException;
import mage.players.net.UserData;
import mage.server.managers.ManagerFactory;
import mage.server.managers.SessionManager;
import org.apache.log4j.Logger;
import org.jboss.remoting.callback.InvokerCallbackHandler;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Server: manage all connections (first connection, after auth, after anon)
 *
 * @author BetaSteward_at_googlemail.com, JayDi85
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
        return Optional.ofNullable(sessions.getOrDefault(sessionId, null));
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
    public boolean connectUser(String sessionId, String restoreSessionId, String userName, String password, String userInfo, boolean detailsMode) throws MageException {
        Session session = sessions.get(sessionId);
        if (session != null) {
            String errorMessage = session.connectUser(userName, password, restoreSessionId);
            if (errorMessage == null) {
                logger.info(userName + " connected to server by sessionId " + sessionId
                        + (restoreSessionId.isEmpty() ? "" : ", restoreSessionId " + restoreSessionId));
                if (detailsMode) {
                    logger.info("- details: " + userInfo);
                }
                logger.debug("- userId:    " + session.getUserId());
                logger.debug("- sessionId: " + sessionId);
                logger.debug("- restoreSessionId: " + restoreSessionId);
                logger.debug("- host:      " + session.getHost());
                return true;
            } else {
                logger.debug(userName + " not connected: " + errorMessage);

                // send error to client side
                try {
                    // ddos/bruteforce protection
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    logger.fatal("waiting of error message had failed", e);
                    Thread.currentThread().interrupt();
                }
                session.sendErrorMessageToClient(errorMessage);
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
    public void disconnect(String sessionId, DisconnectReason reason, boolean checkUserDisconnection) {
        Session session = getSession(sessionId).orElse(null);
        if (session == null) {
            return;
        }

        if (!isValidSession(sessionId)) {
            logger.info("DISCONNECT session, already invalid: " + reason + " - sessionId: " + sessionId);
            // session was removed meanwhile by another thread
            // TODO: otudated code? If no logs on server then remove that code, 2023-12-06
            return;
        }

        if (checkUserDisconnection) {
            managerFactory.userManager().getUser(session.getUserId()).ifPresent(user -> {
                user.onLostConnection(reason);
            });
        }

        sessions.remove(sessionId);
    }

    /**
     * Admin requested the disconnect of a user
     *
     * @param sessionId
     * @param userSessionId
     */
    @Override
    public void disconnectAnother(String sessionId, String userSessionId) {
        if (!checkAdminAccess(sessionId)) {
            return;
        }

        User admin = getUserFromSession(sessionId).orElse(null);
        User user = getUserFromSession(userSessionId).orElse(null);
        if (admin == null || user == null) {
            return;
        }

        user.showUserMessage("Admin action", "Your session was disconnected by admin");
        disconnect(userSessionId, DisconnectReason.DisconnectedByAdmin, true);
        admin.showUserMessage("Admin result", "User " + user.getName() + " was disconnected");
    }

    private Optional<User> getUserFromSession(String sessionId) {
        return getSession(sessionId).flatMap(s -> managerFactory.userManager().getUser(s.getUserId()));
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

    @Override
    public void checkHealth() {
        //logger.info("Checking sessions...");
        // TODO: add lone sessions check and report (with lost user)
    }
}
