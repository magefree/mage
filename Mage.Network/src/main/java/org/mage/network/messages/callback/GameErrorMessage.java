package org.mage.network.messages.callback;

import java.util.UUID;
import org.mage.network.handlers.client.ClientMessageHandler;
import org.mage.network.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class GameErrorMessage extends ClientMessage {
    private final UUID gameId;
    private final String message;

    public GameErrorMessage(UUID gameId, String message) {
        this.gameId = gameId;
        this.message = message;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().gameError(gameId, message);
    }
    
}
