package mage.interfaces.callback;

/**
 * Network: client to process income server commands
 *
 * @author BetaSteward_at_googlemail.com
 */
public interface CallbackClient {

    void onNewConnection();

    void onCallback(ClientCallback callback);
}
