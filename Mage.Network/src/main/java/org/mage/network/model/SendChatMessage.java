package org.mage.network.model;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author BetaSteward
 */
public class SendChatMessage implements Serializable {
    
    private UUID chatId;
    private String message;
    
    public SendChatMessage(UUID chatId, String message) {
        this.chatId = chatId;
        this.message = message;
    }
    
    public UUID getChatId() {
        return chatId;
    }
    
    public String getMessage() {
        return message;
    }
    
}
