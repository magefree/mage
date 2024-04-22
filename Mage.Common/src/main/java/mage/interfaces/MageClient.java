package mage.interfaces;

import mage.interfaces.callback.CallbackClient;
import mage.utils.MageVersion;

/**
 * Network: client side commands to process from a server
 *
 * @author BetaSteward_at_googlemail.com
 */
public interface MageClient extends CallbackClient {

    MageVersion getVersion();

    void connected(String message);

    void disconnected(boolean askToReconnect);

    void showMessage(String message);

    void showError(String message);

}
