package mage.remote.messages.responses;

import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class BooleanResponse extends ClientMessage {
    
    private boolean success;
    
    public BooleanResponse(boolean success) {
        this.success = success;
    }
    
    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.receiveBoolean(success);
    }
    
}
