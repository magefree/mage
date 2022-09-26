package mage.remote.messages.callback;

import java.util.UUID;
import mage.view.DraftPickView;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class DraftPickCallback extends ClientMessage {
    private final UUID draftId;
    private final DraftPickView draftPickView;

    public DraftPickCallback(UUID draftId, DraftPickView draftPickView) {
        this.draftId = draftId;
        this.draftPickView = draftPickView;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().draftPick(draftId, draftPickView);
    }
    
}
