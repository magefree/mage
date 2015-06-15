package org.mage.network.model;

import java.util.UUID;

/**
 *
 * @author BetaSteward
 */
public class SwapSeatRequest extends TableRequest {
    
    private int seatNum1;
    private int seatNum2;
    
    public SwapSeatRequest(UUID roomId, UUID tableId, int seatNum1, int seatNum2) {
        super(roomId, tableId);
        this.seatNum1 = seatNum1;
        this.seatNum2 = seatNum2;
    }
            
    public int getSeatNum1() {
        return seatNum1;
    }

    public int getSeatNum2() {
        return seatNum2;
    }

}
