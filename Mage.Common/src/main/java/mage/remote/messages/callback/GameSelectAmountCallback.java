package mage.remote.messages.callback;

import java.util.UUID;
import mage.view.GameView;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class GameSelectAmountCallback extends ClientMessage {
    private final UUID gameId;
    private final GameView gameView;
    private final String message;
    private final int min;
    private final int max;

    public GameSelectAmountCallback(UUID gameId, GameView gameView, String message, int min, int max) {
        this.gameId = gameId;
        this.gameView = gameView;
        this.message = message;
        this.min = min;
        this.max = max;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().gameSelectAmount(gameId, gameView, message, min, max);
    }
    
}
