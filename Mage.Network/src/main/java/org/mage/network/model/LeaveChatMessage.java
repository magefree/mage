package org.mage.network.model;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author BetaSteward
 */
public class LeaveChatMessage implements Serializable {
    
    private UUID chatId;
    
    public LeaveChatMessage(UUID chatId) {
        this.chatId = chatId;
    }
    
    public UUID getChatId() {
        return chatId;
    }
    
}
