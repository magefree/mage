package mage.remote.messages.callback;

import java.util.UUID;
import mage.view.DraftView;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class DraftUpdateCallback extends ClientMessage {
    private final UUID draftId;
    private final DraftView draftView;

    public DraftUpdateCallback(UUID draftId, DraftView draftView) {
        this.draftId = draftId;
        this.draftView = draftView;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().draftUpdate(draftId, draftView);
    }
    
}
