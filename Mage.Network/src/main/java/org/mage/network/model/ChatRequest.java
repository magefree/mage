package org.mage.network.model;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author BetaSteward
 */
public abstract class ChatRequest implements Serializable {
    
    private UUID chatId;
    
    public ChatRequest(UUID chatId) {
        this.chatId = chatId;
    }
    
    public UUID getChatId() {
        return chatId;
    }
    
}
