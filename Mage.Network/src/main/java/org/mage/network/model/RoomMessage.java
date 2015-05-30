package org.mage.network.model;

import java.io.Serializable;
import mage.view.RoomView;

/**
 *
 * @author BetaSteward
 */
public class RoomMessage implements Serializable {
    
    private RoomView room;
    
    public RoomMessage(RoomView room) {
        this.room = room;
    }
    
    public RoomView getRoom() {
        return room;
    }
    
}
