package org.mage.network.messages.responses;

import org.mage.network.handlers.client.ClientMessageHandler;
import org.mage.network.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class SubmitDeckResponse extends ClientMessage {
    
    private boolean success;
    
    public SubmitDeckResponse(boolean success) {
        this.success = success;
    }
    
    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.receiveBoolean(success);
    }
    
}
