package mage.remote.messages.callback;

import java.util.UUID;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;
import mage.view.GameView;

/**
 *
 * @author BetaSteward
 */
public class GameOverCallback extends ClientMessage {
    private final UUID gameId;
    private final GameView view;
    private final String message;

    public GameOverCallback(UUID gameId, GameView view, String message) {
        this.gameId = gameId;
        this.view = view;
        this.message = message;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().gameOver(gameId, view, message);
    }
    
}
