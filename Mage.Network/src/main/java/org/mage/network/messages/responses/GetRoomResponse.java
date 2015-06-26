package org.mage.network.messages.responses;

import mage.view.RoomView;
import org.mage.network.handlers.client.ClientMessageHandler;
import org.mage.network.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class GetRoomResponse extends ClientMessage {
    
    private RoomView room;
    
    public GetRoomResponse(RoomView room) {
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
