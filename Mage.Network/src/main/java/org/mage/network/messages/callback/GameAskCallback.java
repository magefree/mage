package org.mage.network.messages.callback;

import java.util.UUID;
import mage.view.GameView;
import org.mage.network.handlers.client.ClientMessageHandler;
import org.mage.network.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class GameAskCallback extends ClientMessage {

    private UUID gameId;
    private GameView gameView;
    private String question;
    
    public GameAskCallback(UUID gameId, GameView gameView, String question) {
        this.gameId = gameId;
        this.gameView = gameView;
        this.question = question;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().gameAsk(gameId, gameView, question);
    }
    
}
