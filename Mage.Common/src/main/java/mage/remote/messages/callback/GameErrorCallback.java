package mage.remote.messages.callback;

import java.util.UUID;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class GameErrorCallback extends ClientMessage {
    private final UUID gameId;
    private final String message;

    public GameErrorCallback(UUID gameId, String message) {
        this.gameId = gameId;
        this.message = message;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().gameError(gameId, message);
    }
    
}
