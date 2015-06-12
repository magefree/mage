package org.mage.network.model;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author BetaSteward
 */
public class RemoveTableRequest extends TableRequest {
    
    
    public RemoveTableRequest(UUID roomId, UUID tableId) {
        super(roomId, tableId);
    }
            
}
