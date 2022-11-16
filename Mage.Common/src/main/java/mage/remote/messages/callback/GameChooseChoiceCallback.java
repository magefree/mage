package mage.remote.messages.callback;

import java.util.UUID;
import mage.choices.Choice;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;
import mage.view.GameView;

/**
 *
 * @author BetaSteward
 */
public class GameChooseChoiceCallback extends ClientMessage {
    private final UUID gameId;
    private final GameView gameView;
    private final Choice choice;

    public GameChooseChoiceCallback(UUID gameId, GameView gameView, Choice choice) {
        this.gameId = gameId;
        this.gameView = gameView;
        this.choice = choice;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().gameChooseChoice(gameId, gameView, choice);
    }
    
}
