package mage.remote.messages.callback;

import java.util.UUID;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;

public class ViewSideboardCallback extends ClientMessage {
    private final UUID gameId;
    private final UUID targetPlayerId;
    
    public ViewSideboardCallback(UUID gameId, UUID targetPlayerId){
        this.gameId = gameId;
        this.targetPlayerId = targetPlayerId;
    }
    
    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().viewSideboard(gameId, targetPlayerId);
    }
    
}
