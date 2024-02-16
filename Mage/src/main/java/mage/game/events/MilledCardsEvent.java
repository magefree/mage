package mage.game.events;

import mage.abilities.Ability;
import mage.cards.Cards;
import mage.cards.CardsImpl;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class MilledCardsEvent extends GameEvent {

    private final Cards cards = new CardsImpl();

    public MilledCardsEvent(Ability source, UUID playerId, Cards cards) {
        super(EventType.MILLED_CARDS, playerId, source, playerId);
        this.cards.addAll(cards);
    }

    public Cards getCards() {
        return cards;
    }
}
