package org.mage.network.messages.callback;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import mage.view.GameView;
import org.mage.network.handlers.client.ClientMessageHandler;
import org.mage.network.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class GamePlayManaCallback extends ClientMessage {
    private final UUID gameId;
    private final GameView gameView;
    private final String message;
    private final Map<String, Serializable> options;

    public GamePlayManaCallback(UUID gameId, GameView gameView, String message, Map<String, Serializable> options) {
        this.gameId = gameId;
        this.gameView = gameView;
        this.message = message;
        this.options = options;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().gamePlayMana(gameId, gameView, message, options);
    }
    
}
