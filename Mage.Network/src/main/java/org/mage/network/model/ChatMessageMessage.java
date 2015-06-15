package org.mage.network.model;

import java.util.UUID;
import mage.view.ChatMessage;
import org.mage.network.handlers.client.ClientMessageHandler;

/**
 *
 * @author BetaSteward
 */
public class ChatMessageMessage extends ClientMessage {
    
    private UUID chatId;
    private ChatMessage message;
    
    public ChatMessageMessage(UUID chatId, ChatMessage message) {
        this.chatId = chatId;
        this.message = message;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().receiveChatMessage(chatId, message);
    }
    
}
