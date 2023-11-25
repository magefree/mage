package mage.remote.interfaces;

import mage.remote.Connection;

import java.util.Optional;

/**
 * Network: client side commands for a server
 *
 * @author noxx
 */
public interface Connect {

    String getSessionId();

    String getLastError();

    Optional<String> getServerHostname();

    boolean sendAuthRegister(Connection connection);

    boolean sendAuthSendTokenToEmail(Connection connection);

    boolean sendAuthResetPassword(Connection connection);

    boolean connectStart(Connection connection);

    boolean connectAbort();

    void connectStop(boolean showMessage);

    void connectReconnect(Throwable throwable);

    boolean ping();

    boolean isConnected();

    boolean sendAdminDisconnectUser(String userSessionId);

    boolean sendAdminEndUserSession(String userSessionId);

    boolean sendAdminMuteUserChat(String userName, long durationMinute);

    boolean sendAdminActivateUser(String userName, boolean active);

    boolean sendAdminToggleActivateUser(String userName);

    boolean sendAdminLockUser(String userName, long durationMinute);
}
