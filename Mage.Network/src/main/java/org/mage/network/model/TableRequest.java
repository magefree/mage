package org.mage.network.model;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author BetaSteward
 */
public abstract class TableRequest implements Serializable {

    protected UUID roomId;
    protected UUID tableId;
    
    public TableRequest(UUID roomId, UUID tableId) {
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
