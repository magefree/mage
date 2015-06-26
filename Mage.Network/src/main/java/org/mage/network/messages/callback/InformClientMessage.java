package org.mage.network.messages.callback;

import org.mage.network.handlers.client.ClientMessageHandler;
import org.mage.network.messages.ClientMessage;
import org.mage.network.messages.MessageType;

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
