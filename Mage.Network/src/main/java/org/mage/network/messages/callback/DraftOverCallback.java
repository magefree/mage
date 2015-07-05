package org.mage.network.messages.callback;

import java.util.UUID;
import org.mage.network.handlers.client.ClientMessageHandler;
import org.mage.network.messages.ClientMessage;

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
