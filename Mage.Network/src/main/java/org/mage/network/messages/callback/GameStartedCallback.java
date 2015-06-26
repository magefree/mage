package org.mage.network.messages.callback;

import java.util.UUID;
import org.mage.network.handlers.client.ClientMessageHandler;
import org.mage.network.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class GameStartedCallback extends ClientMessage {
    
    private UUID gameId;
    private UUID playerId;
    
    public GameStartedCallback(UUID gameId, UUID playerId) {
        this.gameId = gameId;
        this.playerId = playerId;
    }
    
    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().gameStarted(gameId, playerId);
    }
}
