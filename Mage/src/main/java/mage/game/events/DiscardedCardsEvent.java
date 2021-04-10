package mage.game.events;

import mage.abilities.Ability;
import mage.cards.Cards;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public class DiscardedCardsEvent extends GameEvent {

    private final Cards discardedCards;

    public DiscardedCardsEvent(UUID targetId, Ability source, UUID playerId, int amount, Cards discardedCards) {
        super(EventType.DISCARDED_CARDS, targetId, source, playerId, amount, false);
        this.discardedCards = discardedCards;
    }

    public Cards getDiscardedCards() {
        return discardedCards;
    }
}
