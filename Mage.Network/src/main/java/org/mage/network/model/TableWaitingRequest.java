package org.mage.network.model;

import java.util.UUID;

/**
 *
 * @author BetaSteward
 */
public class TableWaitingRequest extends TableRequest {

    public TableWaitingRequest(UUID roomId, UUID tableId) {
        super(roomId, tableId);
    }
    
}
