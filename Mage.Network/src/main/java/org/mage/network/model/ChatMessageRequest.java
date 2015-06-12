package org.mage.network.model;

import java.util.UUID;

/**
 *
 * @author BetaSteward
 */
public class ChatMessageRequest extends ChatRequest {
    
    private String message;
    
    public ChatMessageRequest(UUID chatId, String message) {
        super(chatId);
        this.message = message;
    }
        
    public String getMessage() {
        return message;
    }
    
}
