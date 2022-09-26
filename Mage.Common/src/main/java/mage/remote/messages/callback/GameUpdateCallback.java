package mage.remote.messages.callback;

import java.util.UUID;
import mage.view.GameView;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class GameUpdateCallback extends ClientMessage {
    private final UUID gameId;
    private final GameView view;

    public GameUpdateCallback(UUID gameId, GameView view) {
        this.gameId = gameId;
        this.view = view;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().gameUpdate(gameId, view);
    }
    
}
