package org.mage.network.messages;

import java.util.UUID;
import mage.game.Table;
import mage.view.GameEndView;
import org.mage.network.handlers.client.ClientMessageHandler;

/**
 *
 * @author BetaSteward
 */
public class GameEndInfoMessage extends ClientMessage {
    private final UUID gameId;
    private final GameEndView view;

    public GameEndInfoMessage(UUID gameId, GameEndView view) {
        this.gameId = gameId;
        this.view = view;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().gameEndInfo(gameId, view);
    }
    
}
