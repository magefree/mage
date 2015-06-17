package org.mage.network.model;

import java.util.UUID;
import org.mage.network.handlers.client.ClientMessageHandler;

/**
 *
 * @author BetaSteward
 */
public class JoinGameMessage extends ClientMessage {
    
    private UUID chatId;
    
    public JoinGameMessage(UUID chatId) {
        this.chatId = chatId;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.receiveId(chatId);
    }

}