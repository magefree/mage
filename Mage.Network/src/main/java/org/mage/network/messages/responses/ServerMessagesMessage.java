package org.mage.network.messages.responses;

import java.util.List;
import org.mage.network.handlers.client.ClientMessageHandler;
import org.mage.network.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class ServerMessagesMessage extends ClientMessage {
    
    private List<String> messages;
    
    public ServerMessagesMessage(List<String> messages) {
        this.messages = messages;
    }
    
    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.receiveStringList(messages);
    }
    
}
