package org.mage.network.messages.responses;

import mage.view.DraftPickView;
import org.mage.network.handlers.client.ClientMessageHandler;
import org.mage.network.messages.ClientMessage;

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
