package org.mage.network.messages.callback;

import java.util.UUID;
import mage.view.GameView;
import org.mage.network.handlers.client.ClientMessageHandler;
import org.mage.network.messages.ClientMessage;

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
