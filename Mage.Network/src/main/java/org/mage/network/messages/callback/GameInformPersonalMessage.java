package org.mage.network.messages.callback;

import java.util.UUID;
import mage.view.GameClientMessage;
import org.mage.network.handlers.client.ClientMessageHandler;
import org.mage.network.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class GameInformPersonalMessage extends ClientMessage {
    private final UUID gameId;
    private final GameClientMessage message;

    public GameInformPersonalMessage(UUID gameId, GameClientMessage message) {
        this.gameId = gameId;
        this.message = message;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().gameInformPersonal(gameId, message);
    }
    
}
