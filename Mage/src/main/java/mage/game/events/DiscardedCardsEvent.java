package mage.game.events;

import mage.abilities.Ability;
import mage.cards.Cards;
import mage.cards.CardsImpl;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public class DiscardedCardsEvent extends GameEvent {

    private final Cards discardedCards;

    public DiscardedCardsEvent(Ability source, UUID playerId, int amount, Cards discardedCards) {
        super(EventType.DISCARDED_CARDS, null, source, playerId, amount, false);
        this.discardedCards = new CardsImpl(discardedCards);
    }

    public Cards getDiscardedCards() {
        return discardedCards;
    }
}
