package org.mage.network.model;

import java.util.UUID;
import org.mage.network.handlers.client.ClientMessageHandler;

/**
 *
 * @author BetaSteward
 */
public class GameSelectAmountMessage extends ClientMessage {
    private final UUID gameId;
    private final String message;
    private final int min;
    private final int max;

    public GameSelectAmountMessage(UUID gameId, String message, int min, int max) {
        this.gameId = gameId;
        this.message = message;
        this.min = min;
        this.max = max;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().gameSelectAmount(gameId, message, min, max);
    }
    
}
