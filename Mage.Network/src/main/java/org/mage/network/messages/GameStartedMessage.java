package org.mage.network.messages;

import java.util.UUID;
import org.mage.network.handlers.client.ClientMessageHandler;

/**
 *
 * @author BetaSteward
 */
public class GameStartedMessage extends ClientMessage {
    
    private UUID gameId;
    private UUID playerId;
    
    public GameStartedMessage(UUID gameId, UUID playerId) {
        this.gameId = gameId;
        this.playerId = playerId;
    }
    
    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().gameStarted(gameId, playerId);
    }
}
