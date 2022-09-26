package mage.remote.messages.callback;

import java.util.UUID;
import mage.view.GameView;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class GamePlayXManaCallback extends ClientMessage {
    private final UUID gameId;
    private final GameView gameView;
    private final String message;

    public GamePlayXManaCallback(UUID gameId, GameView gameView, String message) {
        this.gameId = gameId;
        this.gameView = gameView;
        this.message = message;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().gamePlayXMana(gameId, gameView, message);
    }
    
}
