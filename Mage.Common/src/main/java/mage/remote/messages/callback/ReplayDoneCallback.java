package mage.remote.messages.callback;

import java.util.UUID;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;

public class ReplayDoneCallback extends ClientMessage{
    private final UUID gameId;
    private final String result;
    
    public ReplayDoneCallback(UUID gameId, String result){
        this.gameId = gameId;
        this.result = result;
    }
    
    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().replayDone(gameId, result);
    }
    
}
