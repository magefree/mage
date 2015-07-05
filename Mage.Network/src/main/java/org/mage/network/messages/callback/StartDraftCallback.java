package org.mage.network.messages.callback;

import java.util.UUID;
import org.mage.network.handlers.client.ClientMessageHandler;
import org.mage.network.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class StartDraftCallback extends ClientMessage {
    private final UUID draftId;
    private final UUID playerId;

    public StartDraftCallback(UUID draftId, UUID playerId) {
        this.draftId = draftId;
        this.playerId = playerId;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().startDraft(draftId, playerId);
    }
    
}
