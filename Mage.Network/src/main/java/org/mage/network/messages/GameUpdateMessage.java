package org.mage.network.messages;

import java.util.UUID;
import mage.view.GameView;
import org.mage.network.handlers.client.ClientMessageHandler;

/**
 *
 * @author BetaSteward
 */
public class GameUpdateMessage extends ClientMessage {
    private final UUID gameId;
    private final GameView view;

    public GameUpdateMessage(UUID gameId, GameView view) {
        this.gameId = gameId;
        this.view = view;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().gameUpdate(gameId, view);
    }
    
}
