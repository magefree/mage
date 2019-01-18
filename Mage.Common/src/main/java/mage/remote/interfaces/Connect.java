package mage.remote.interfaces;

import mage.remote.Connection;

import java.util.Optional;

/**
 * @author noxx
 */
public interface Connect {

    boolean register(Connection connection);

    boolean emailAuthToken(Connection connection);

    boolean resetPassword(Connection connection);

    boolean connect(Connection connection);

    boolean stopConnecting();

    void disconnect(boolean showMessage);

    void reconnect(Throwable throwable);

    boolean ping();

    boolean isConnected();

    Optional<String> getServerHostname();

    boolean disconnectUser(String userSessionId);

    boolean endUserSession(String userSessionId);

    boolean muteUserChat(String userName, long durationMinute);

    boolean setActivation(String userName, boolean active);

    boolean toggleActivation(String userName);

    boolean lockUser(String userName, long durationMinute);

    String getSessionId();

    String getLastError();
}
