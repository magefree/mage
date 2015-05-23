package org.mage.network.model;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author BetaSteward
 */
public class ChatRoomIdMessage implements Serializable {
    
    private UUID id;
    
    public ChatRoomIdMessage(UUID id) {
        this.id = id;
    }
    
    public UUID getId() {
        return id;
    }
    
}