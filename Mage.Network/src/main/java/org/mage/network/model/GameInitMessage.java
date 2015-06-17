package org.mage.network.model;

import java.util.UUID;
import mage.view.GameView;
import org.mage.network.handlers.client.ClientMessageHandler;

/**
 *
 * @author BetaSteward
 */
public class GameInitMessage extends ClientMessage {
    
    private UUID gameId;
    private GameView gameView;
    
    public GameInitMessage(UUID gameId, GameView gameView) {
        this.gameId = gameId;
        this.gameView = gameView;
    }
    
    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().initGame(gameId, gameView);
    }
}
