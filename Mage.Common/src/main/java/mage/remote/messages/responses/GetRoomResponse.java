package mage.remote.messages.responses;

import mage.view.RoomView;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;

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
