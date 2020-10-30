package mage.server.managers;

import mage.server.AuthorizedUser;
import mage.server.DisconnectReason;
import mage.server.User;
import mage.view.UserView;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserManager {
    Optional<User> createUser(String userName, String host, AuthorizedUser authorizedUser);

    Optional<User> getUser(UUID userId);

    Optional<User> getUserByName(String userName);

    Collection<User> getUsers();

    boolean connectToSession(String sessionId, UUID userId);

    void disconnect(UUID userId, DisconnectReason reason);

    boolean isAdmin(UUID userId);

    void removeUserFromAllTablesAndChat(UUID userId, DisconnectReason reason);

    void informUserOpponents(UUID userId, String message);

    boolean extendUserSession(UUID userId, String pingInfo);

    List<UserView> getUserInfoList();

    void handleException(Exception ex);

    String getUserHistory(String userName);

    void updateUserHistory();
}
