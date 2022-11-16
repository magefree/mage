package mage.remote.messages.callback;

import java.util.UUID;
import mage.view.GameView;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;


public class ReplayInitCallback extends ClientMessage {
    private final UUID gameId;
    private final GameView game;
        
    public ReplayInitCallback(UUID gameId, GameView game) {
        this.gameId = gameId;
        this.game = game;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().replayInit(gameId, game);
    }
    
}
