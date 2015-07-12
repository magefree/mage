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
public class GameAskCallback extends ClientMessage {

    private final UUID gameId;
    private final GameView gameView;
    private final String question;
    private final Map<String, Serializable> options;
    
    public GameAskCallback(UUID gameId, GameView gameView, String question, Map<String, Serializable> options) {
        this.gameId = gameId;
        this.gameView = gameView;
        this.question = question;
        this.options = options;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().gameAsk(gameId, gameView, question, options);
    }
    
}
