package org.mage.network.model;

import java.util.UUID;
import mage.view.GameView;
import org.mage.network.handlers.client.ClientMessageHandler;

/**
 *
 * @author BetaSteward
 */
public class GameAskMessage extends ClientMessage {

    private UUID gameId;
    private GameView gameView;
    private String question;
    
    public GameAskMessage(UUID gameId, GameView gameView, String question) {
        this.gameId = gameId;
        this.gameView = gameView;
        this.question = question;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().gameAsk(gameId, gameView, question);
    }
    
}
