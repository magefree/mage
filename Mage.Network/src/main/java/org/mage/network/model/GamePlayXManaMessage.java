package org.mage.network.model;

import java.util.UUID;
import mage.view.GameView;
import org.mage.network.handlers.client.ClientMessageHandler;

/**
 *
 * @author BetaSteward
 */
public class GamePlayXManaMessage extends ClientMessage {
    private final UUID gameId;
    private final GameView gameView;
    private final String message;

    public GamePlayXManaMessage(UUID gameId, GameView gameView, String message) {
        this.gameId = gameId;
        this.gameView = gameView;
        this.message = message;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().gamePlayXMana(gameId, gameView, message);
    }
    
}
