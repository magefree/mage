package org.mage.network.model;

import java.util.UUID;

/**
 *
 * @author BetaSteward
 */
public class JoinChatRequest extends ChatRequest {
    
    public JoinChatRequest(UUID chatId) {
        super(chatId);
    }
    
}
