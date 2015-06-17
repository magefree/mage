package org.mage.network.model;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import mage.view.GameView;
import org.mage.network.handlers.client.ClientMessageHandler;

/**
 *
 * @author BetaSteward
 */
public class GameSelectMessage extends ClientMessage {
    private final UUID gameId;
    private final GameView gameView;
    private final String message;
    private final Map<String, Serializable> options;

    public GameSelectMessage(UUID gameId, GameView gameView, String message, Map<String, Serializable> options) {
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
