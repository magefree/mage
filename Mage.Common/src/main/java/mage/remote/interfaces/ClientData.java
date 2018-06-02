
package mage.remote.interfaces;

import mage.players.net.UserData;

/**
 * @author noxx
 */
public interface ClientData {

    String getUserName();

    boolean updatePreferencesForServer(UserData userData);

    void setJsonLogActive(boolean active);

    boolean isJsonLogActive();
}
