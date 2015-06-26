package org.mage.network.messages.callback;

import java.util.UUID;
import mage.view.ChatMessage;
import org.mage.network.handlers.client.ClientMessageHandler;
import org.mage.network.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class ChatMessageCallback extends ClientMessage {
    
    private UUID chatId;
    private ChatMessage message;
    
    public ChatMessageCallback(UUID chatId, ChatMessage message) {
        this.chatId = chatId;
        this.message = message;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().receiveChatMessage(chatId, message);
    }
    
}
