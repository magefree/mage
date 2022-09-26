package mage.remote.messages.responses;

import java.util.List;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class ServerMessagesResponse extends ClientMessage {
    
    private Object messages;
    
    public ServerMessagesResponse(Object messages) {
        this.messages = messages;
    }
    
    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.receiveObject(messages);
    }
    
}
