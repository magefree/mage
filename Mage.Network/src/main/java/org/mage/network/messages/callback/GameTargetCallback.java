package org.mage.network.messages.callback;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.view.CardsView;
import mage.view.GameView;
import org.mage.network.handlers.client.ClientMessageHandler;
import org.mage.network.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class GameTargetCallback extends ClientMessage {
    
    private final UUID gameId;
    private final GameView gameView;
    private final String question;
    private final CardsView cardView;
    private final Set<UUID> targets;
    private final boolean required;
    private final Map<String, Serializable> options;

    public GameTargetCallback(UUID gameId, GameView gameView, String question, CardsView cardView, Set<UUID> targets, boolean required, Map<String, Serializable> options) {
        this.gameId = gameId;
        this.gameView = gameView;
        this.question = question;
        this.cardView = cardView;
        this.targets = targets;
        this.required = required;
        this.options = options;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().gameTarget(gameId, gameView, question, cardView, targets, required, options);
    }
    
}
