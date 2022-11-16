package mage.remote.messages.callback;

import java.util.UUID;
import mage.view.GameView;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class GameInitCallback extends ClientMessage {
    
    private UUID gameId;
    private GameView gameView;
    
    public GameInitCallback(UUID gameId, GameView gameView) {
        this.gameId = gameId;
        this.gameView = gameView;
    }
    
    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().initGame(gameId, gameView);
    }
}
