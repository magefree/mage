package org.mage.network.model;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author BetaSteward
 */
public class JoinChatMessage implements Serializable {
    
    private UUID chatId;
    
    public JoinChatMessage(UUID chatId) {
        this.chatId = chatId;
    }
    
    public UUID getChatId() {
        return chatId;
    }
    
}
