package org.mage.network.model;

import java.util.UUID;
import org.mage.network.handlers.client.ClientMessageHandler;

/**
 *
 * @author BetaSteward
 */
public class ChatRoomIdMessage extends ClientMessage {
    
    private UUID chatId;
    
    public ChatRoomIdMessage(UUID chatId) {
        this.chatId = chatId;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.receiveId(chatId);
    }

}