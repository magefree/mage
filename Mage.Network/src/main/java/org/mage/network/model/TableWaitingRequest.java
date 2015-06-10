package org.mage.network.model;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author BetaSteward
 */
public class TableWaitingRequest implements Serializable {
    
    private UUID roomId;
    private UUID tableId;
    
    public TableWaitingRequest(UUID roomId, UUID tableId) {
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
