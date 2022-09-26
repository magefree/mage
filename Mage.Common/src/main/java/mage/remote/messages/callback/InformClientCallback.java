package mage.remote.messages.callback;

import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;
import mage.remote.messages.MessageType;

/**
 *
 * @author BetaSteward
 */
public class InformClientCallback extends ClientMessage {
    
    private String title;
    private String message;
    private MessageType type;
    
    public InformClientCallback(String title, String message, MessageType type) {
        this.title = title;
        this.message = message;
        this.type = type;
    }
        
    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().inform(title, message, type);
    }
}
