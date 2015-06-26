package org.mage.network.messages.callback;

import java.util.UUID;
import mage.view.GameView;
import org.mage.network.handlers.client.ClientMessageHandler;
import org.mage.network.messages.ClientMessage;

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
