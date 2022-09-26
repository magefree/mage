package mage.remote.messages.callback;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import mage.view.GameView;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class GameSelectCallback extends ClientMessage {
    private final UUID gameId;
    private final GameView gameView;
    private final String message;
    private final Map<String, Serializable> options;

    public GameSelectCallback(UUID gameId, GameView gameView, String message, Map<String, Serializable> options) {
        this.gameId = gameId;
        this.gameView = gameView;
        this.message = message;
        this.options = options;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().gameSelect(gameId, gameView, message, options);
    }
    
}
