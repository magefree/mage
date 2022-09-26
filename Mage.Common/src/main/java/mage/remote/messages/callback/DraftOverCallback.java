package mage.remote.messages.callback;

import java.util.UUID;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class DraftOverCallback extends ClientMessage {
    private final UUID draftId;

    public DraftOverCallback(UUID draftId) {
        this.draftId = draftId;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().draftOver(draftId);
    }
    
}
