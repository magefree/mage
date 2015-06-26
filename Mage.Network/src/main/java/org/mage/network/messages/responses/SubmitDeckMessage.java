package org.mage.network.messages.responses;

import org.mage.network.handlers.client.ClientMessageHandler;
import org.mage.network.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class SubmitDeckMessage extends ClientMessage {
    
    private boolean success;
    
    public SubmitDeckMessage(boolean success) {
        this.success = success;
    }
    
    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.receiveBoolean(success);
    }
    
}
