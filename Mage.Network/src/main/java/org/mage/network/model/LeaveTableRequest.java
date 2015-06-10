package org.mage.network.model;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author BetaSteward
 */
public class LeaveTableRequest implements Serializable {
    
    private UUID roomId;
    private UUID tableId;
    
    public LeaveTableRequest(UUID roomId, UUID tableId) {
        this.roomId = roomId;
        this.tableId = tableId;
    }
    
    public UUID getRoomId() {
        return roomId;
    }

    public UUID getTableId() {
        return tableId;
    }
        
}
