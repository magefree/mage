package org.mage.network.model;

import java.io.Serializable;
import java.util.UUID;
import mage.view.ChatMessage;

/**
 *
 * @author BetaSteward
 */
public class ReceiveChatMessage implements Serializable {
    
    private UUID chatId;
    private ChatMessage message;
    
    public ReceiveChatMessage(UUID chatId, ChatMessage message) {
        this.chatId = chatId;
        this.message = message;
    }
    
    public UUID getChatId() {
        return chatId;
    }
    
    public ChatMessage getMessage() {
        return message;
    }
    
}
