package org.mage.network.messages.callback;

import java.util.UUID;
import mage.view.GameView;
import org.mage.network.handlers.client.ClientMessageHandler;
import org.mage.network.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class WatchGameCallback extends ClientMessage {
    private final UUID gameId;
    private final UUID chatId;
    private final GameView game;

    public WatchGameCallback(UUID gameId, UUID chatId, GameView game) {
        this.gameId = gameId;
        this.chatId = chatId;
        this.game = game;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().watchGame(gameId, chatId, game);
    }
    
}
