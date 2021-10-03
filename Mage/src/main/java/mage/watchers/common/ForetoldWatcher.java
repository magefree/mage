package mage.watchers.common;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.cards.Card;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;
import mage.watchers.Watcher;

/**
 *
 * @author jeffwadsworth
 */
public class ForetoldWatcher extends Watcher {

    // If foretell was activated or a card was Foretold by the controller this turn, this list stores it.  Cleared at the end of the turn.
    private final Set<UUID> foretellCardsThisTurn = new HashSet<>();
    private final Set<UUID> foretoldCards = new HashSet<>();

    public ForetoldWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.FORETELL) {
            Card card = game.getCard(event.getTargetId());
            if (card != null
                    && controllerId == event.getPlayerId()) {
                foretellCardsThisTurn.add(card.getId());
                foretoldCards.add(card.getId());
            }
        }
        // Ethereal Valkyrie
        if (event.getType() == GameEvent.EventType.FORETOLD) {
            Card card = game.getCard(event.getTargetId());
            if (card != null) {
                // Ethereal Valkyrie does not Foretell the card, it becomes Foretold, so don't add it to the Foretell list
                foretoldCards.add(card.getId());
            }
        }
    }

    public boolean cardWasForetold(UUID sourceId) {
        return foretoldCards.contains(sourceId);
    }

    public int countNumberForetellThisTurn() {
        return foretellCardsThisTurn.size();
    }

    @Override
    public void reset() {
        super.reset();
        foretellCardsThisTurn.clear();
    }
}
