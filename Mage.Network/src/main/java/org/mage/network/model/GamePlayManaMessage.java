package org.mage.network.model;

import java.util.UUID;
import mage.view.GameView;
import org.mage.network.handlers.client.ClientMessageHandler;

/**
 *
 * @author BetaSteward
 */
public class GamePlayManaMessage extends ClientMessage {
    private final UUID gameId;
    private final GameView gameView;
    private final String message;

    public GamePlayManaMessage(UUID gameId, GameView gameView, String message) {
        this.gameId = gameId;
        this.gameView = gameView;
        this.message = message;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().gamePlayMana(gameId, gameView, message);
    }
    
}
