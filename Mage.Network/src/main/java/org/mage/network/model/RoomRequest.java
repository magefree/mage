package org.mage.network.model;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author BetaSteward
 */
public abstract class RoomRequest implements Serializable {
    
    private UUID roomId;
    
    public RoomRequest(UUID roomId) {
        this.roomId = roomId;
    }
    
    public UUID getRoomId() {
        return roomId;
    }
    
}
