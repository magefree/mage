package mage.server.managers;

import mage.MageException;
import mage.players.net.UserData;
import mage.server.DisconnectReason;
import mage.server.Session;
import mage.server.User;
import org.jboss.remoting.callback.InvokerCallbackHandler;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface SessionManager {

    Optional<Session> getSession(@Nonnull String sessionId);

    void createSession(String sessionId, InvokerCallbackHandler callbackHandler);

    boolean registerUser(String sessionId, String userName, String password, String email) throws MageException;

    boolean connectUser(String sessionId, String userName, String password, String userIdStr) throws MageException;

    boolean connectAdmin(String sessionId);

    boolean setUserData(String userName, String sessionId, UserData userData, String clientVersion, String userIdStr) throws MageException;

    void disconnect(String sessionId, DisconnectReason reason);

    void disconnect(String sessionId, DisconnectReason reason, Session directSession);

    void disconnectUser(String sessionId, String userSessionId);

    void endUserSession(String sessionId, String userSessionId);

    boolean checkAdminAccess(String sessionId);

    boolean isValidSession(@Nonnull String sessionId);

    Optional<User> getUser(@Nonnull String sessionId);

    boolean extendUserSession(String sessionId, String pingInfo);

    void sendErrorMessageToClient(String sessionId, String message);
}
