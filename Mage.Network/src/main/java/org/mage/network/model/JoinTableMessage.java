package org.mage.network.model;

import org.mage.network.handlers.client.ClientMessageHandler;

/**
 *
 * @author BetaSteward
 */
public class JoinTableMessage extends ClientMessage {
    
    private boolean success;
    
    public JoinTableMessage(boolean success) {
        this.success = success;
    }
    
    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.receiveBoolean(success);
    }
    
}
