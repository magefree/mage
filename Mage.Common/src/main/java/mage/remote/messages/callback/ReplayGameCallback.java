package mage.remote.messages.callback;

import java.util.UUID;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;

public class ReplayGameCallback extends ClientMessage {
    private final UUID gameId;
    
    public ReplayGameCallback(UUID gameId){
        this.gameId = gameId;
    }
    
    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().replayGame(gameId);
    }
}
