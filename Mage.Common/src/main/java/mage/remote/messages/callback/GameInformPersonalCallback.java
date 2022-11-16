package mage.remote.messages.callback;

import java.util.UUID;
import mage.view.GameClientMessage;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class GameInformPersonalCallback extends ClientMessage {
    private final UUID gameId;
    private final GameClientMessage message;

    public GameInformPersonalCallback(UUID gameId, GameClientMessage message) {
        this.gameId = gameId;
        this.message = message;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().gameInformPersonal(gameId, message);
    }
    
}
