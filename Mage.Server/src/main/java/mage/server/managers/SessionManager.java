package mage.server.managers;

import mage.players.net.UserData;
import mage.remote.DisconnectReason;
import mage.server.Session;
import mage.server.User;

import javax.annotation.Nonnull;
import java.util.Optional;
import mage.remote.Connection;

public interface SessionManager {
    Optional<Session> getSession(@Nonnull String sessionId);

    boolean registerUser(String sessionId, Connection connection, String host);

    boolean connectUser(String sessionId, Connection connection, String host);

    boolean connectAdmin(String sessionId);

    boolean setUserData(String sessionId, UserData userData, String clientVersion, String userIdStr);

    void disconnect(String sessionId, DisconnectReason reason);

    void disconnect(String sessionId, DisconnectReason reason, Session directSession);

    void disconnectUser(String sessionId, String userSessionId);

    void endUserSession(String sessionId, String userSessionId);

    boolean isAdmin(String sessionId);

    boolean isValidSession(@Nonnull String sessionId);

    Optional<User> getUser(@Nonnull String sessionId);

    boolean extendUserSession(String sessionId, String pingInfo);

    void sendErrorMessageToClient(String sessionId, String message);
}
