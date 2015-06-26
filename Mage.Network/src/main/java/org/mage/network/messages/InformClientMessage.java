package org.mage.network.messages;

import org.mage.network.handlers.client.ClientMessageHandler;

/**
 *
 * @author BetaSteward
 */
public class InformClientMessage extends ClientMessage {
    
    private String title;
    private String message;
    private MessageType type;
    
    public InformClientMessage(String title, String message, MessageType type) {
        this.title = title;
        this.message = message;
        this.type = type;
    }
        
    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().inform(title, message, type);
    }
}
