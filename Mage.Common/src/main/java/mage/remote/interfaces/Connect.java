package mage.remote.interfaces;

import mage.remote.Connection;

/**
 * Network: client side commands for a server
 *
 * @author noxx, JayDi85
 */
public interface Connect {

    String getSessionId();

    void setRestoreSessionId(String restoreSessionId);

    String getLastError();

    String getServerHost();

    boolean sendAuthRegister(Connection connection);

    boolean sendAuthSendTokenToEmail(Connection connection);

    boolean sendAuthResetPassword(Connection connection);

    boolean connectStart(Connection connection);

    boolean connectAbort();

    void connectStop(boolean askForReconnect, boolean keepMySessionActive);

    void connectReconnect(Throwable throwable);

    void ping();

    boolean isConnected();

    boolean sendAdminDisconnectUser(String userSessionId);

    boolean sendAdminEndUserSession(String userSessionId);

    boolean sendAdminMuteUserChat(String userName, long durationMinute);

    boolean sendAdminActivateUser(String userName, boolean active);

    boolean sendAdminToggleActivateUser(String userName);

    boolean sendAdminLockUser(String userName, long durationMinute);
}
