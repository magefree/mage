package org.mage.network.model;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author BetaSteward
 */
public class ChatRoomIdRequest implements Serializable {
    
    private UUID id;
    
    public ChatRoomIdRequest(UUID id) {
        this.id = id;
    }
    
    public UUID getId() {
        return id;
    }
    
}
