package org.mage.network.messages.responses;

import java.util.UUID;
import org.mage.network.handlers.client.ClientMessageHandler;
import org.mage.network.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class JoinGameResponse extends ClientMessage {
    
    private UUID chatId;
    
    public JoinGameResponse(UUID chatId) {
        this.chatId = chatId;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.receiveId(chatId);
    }

}