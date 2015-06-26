package org.mage.network.messages.callback;

import java.util.UUID;
import mage.game.Table;
import mage.view.GameEndView;
import org.mage.network.handlers.client.ClientMessageHandler;
import org.mage.network.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class GameEndInfoCallback extends ClientMessage {
    private final UUID gameId;
    private final GameEndView view;

    public GameEndInfoCallback(UUID gameId, GameEndView view) {
        this.gameId = gameId;
        this.view = view;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().gameEndInfo(gameId, view);
    }
    
}
