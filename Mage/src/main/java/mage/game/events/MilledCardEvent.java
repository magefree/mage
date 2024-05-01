package mage.game.events;

import mage.abilities.Ability;
import mage.cards.Card;

import java.util.UUID;

/**
 * Event for an individual card being milled.
 * Stores the card at the moment it is milled.
 *
 * @author Susucr
 */
public class MilledCardEvent extends GameEvent {

    private final Card card;

    public MilledCardEvent(Card card, UUID playerId, Ability source) {
        super(EventType.MILLED_CARD, card.getId(), source, playerId);
        this.card = card;
    }

    public Card getCard() {
        return card;
    }
}
