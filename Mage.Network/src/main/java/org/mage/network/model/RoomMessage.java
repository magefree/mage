package org.mage.network.model;

import mage.view.RoomView;
import org.mage.network.handlers.client.ClientMessageHandler;

/**
 *
 * @author BetaSteward
 */
public class RoomMessage extends ClientMessage {
    
    private RoomView room;
    
    public RoomMessage(RoomView room) {
        this.room = room;
    }
    
    public RoomView getRoom() {
        return room;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.receiveRoomView(room);
    }
    
}
