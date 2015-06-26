package org.mage.network.messages;

import java.util.UUID;
import mage.view.GameClientMessage;
import org.mage.network.handlers.client.ClientMessageHandler;

/**
 *
 * @author BetaSteward
 */
public class GameInformMessage extends ClientMessage {
    private final UUID gameId;
    private final GameClientMessage message;

    public GameInformMessage(UUID gameId, GameClientMessage message) {
        this.gameId = gameId;
        this.message = message;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().gameInform(gameId, message);
    }
    
}
