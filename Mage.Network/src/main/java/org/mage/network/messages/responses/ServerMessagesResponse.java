package org.mage.network.messages.responses;

import java.util.List;
import org.mage.network.handlers.client.ClientMessageHandler;
import org.mage.network.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class ServerMessagesResponse extends ClientMessage {
    
    private List<String> messages;
    
    public ServerMessagesResponse(List<String> messages) {
        this.messages = messages;
    }
    
    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.receiveStringList(messages);
    }
    
}
