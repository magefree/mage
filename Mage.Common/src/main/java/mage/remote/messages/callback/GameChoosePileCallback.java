package mage.remote.messages.callback;

import java.util.UUID;
import mage.view.CardsView;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;
import mage.view.GameView;

/**
 *
 * @author BetaSteward
 */
public class GameChoosePileCallback extends ClientMessage {
    private final UUID gameId;
    private final GameView gameView;
    private final String message;
    private final CardsView pile1;
    private final CardsView pile2;

    public GameChoosePileCallback(UUID gameId, GameView gameView, String message, CardsView pile1, CardsView pile2) {
        this.gameId = gameId;
        this.gameView = gameView;
        this.message = message;
        this.pile1 = pile1;
        this.pile2 = pile2;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().gameChoosePile(gameId, gameView, message, pile1, pile2);
    }
    
}
