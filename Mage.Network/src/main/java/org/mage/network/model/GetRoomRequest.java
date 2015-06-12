package org.mage.network.model;

import java.util.UUID;

/**
 *
 * @author BetaSteward
 */
public class GetRoomRequest extends RoomRequest {
    
    public GetRoomRequest(UUID roomId) {
        super(roomId);
    }
        
}
