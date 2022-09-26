package mage.remote.messages.responses;

import mage.view.DraftPickView;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class PickCardResponse extends ClientMessage {
    
    private DraftPickView view;
    
    public PickCardResponse(DraftPickView view) {
        this.view = view;
    }
    
    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.receiveDraftPickView(view);
    }
    
}
