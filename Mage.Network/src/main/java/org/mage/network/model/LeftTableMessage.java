package org.mage.network.model;

import org.mage.network.handlers.client.ClientMessageHandler;

/**
 *
 * @author BetaSteward
 */
public class LeftTableMessage extends ClientMessage {
    
    private boolean success;
    
    public LeftTableMessage(boolean success) {
        this.success = success;
    }
    
    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.receiveBoolean(success);
    }
    
}
