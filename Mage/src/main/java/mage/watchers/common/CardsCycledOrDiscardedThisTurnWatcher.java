
package mage.watchers.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.MageObjectReference;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

/**
 * Stores cards that were cycled or discarded by any player this turn.
 *
 * @author jeffwadsworth
 */
public class CardsCycledOrDiscardedThisTurnWatcher extends Watcher {

    private final Map<UUID, Cards> cycledOrDiscardedCardsThisTurn = new HashMap<>();
    private final Map<UUID, Set<MageObjectReference>> numberOfCycledOrDiscardedCardsThisTurn = new HashMap<>();

    public CardsCycledOrDiscardedThisTurnWatcher() {
        super(WatcherScope.GAME);
    }


    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DISCARDED_CARD
                || event.getType() == GameEvent.EventType.CYCLED_CARD
                && event.getPlayerId() != null) {
            Card card = game.getCard(event.getTargetId());
            if (card != null) {
                Cards c = getCardsCycledOrDiscardedThisTurn(event.getPlayerId());
                c.add(card);
                cycledOrDiscardedCardsThisTurn.put(event.getPlayerId(), c);
                Set<MageObjectReference> cycledOrDiscardedCards = numberOfCycledOrDiscardedCardsThisTurn.get(event.getPlayerId());
                if (cycledOrDiscardedCards == null) {
                    cycledOrDiscardedCards = new HashSet<>();
                    numberOfCycledOrDiscardedCardsThisTurn.put(event.getPlayerId(), cycledOrDiscardedCards);
                }
                cycledOrDiscardedCards.add(new MageObjectReference(card, game));
            }
        }
    }

    public Cards getCardsCycledOrDiscardedThisTurn(UUID playerId) {
        return cycledOrDiscardedCardsThisTurn.getOrDefault(playerId, new CardsImpl());
    }

    public int getNumberOfCardsCycledOrDiscardedThisTurn(UUID playerId) {
        if (numberOfCycledOrDiscardedCardsThisTurn.containsKey(playerId)) {
            return numberOfCycledOrDiscardedCardsThisTurn.get(playerId).size();
        }
        return 0;
    }

    @Override
    public void reset() {
        super.reset();
        cycledOrDiscardedCardsThisTurn.clear();
        numberOfCycledOrDiscardedCardsThisTurn.clear();
    }

}
