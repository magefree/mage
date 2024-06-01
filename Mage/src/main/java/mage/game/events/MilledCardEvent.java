package mage.game.events;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.game.Game;

import java.util.UUID;

/**
 * Event for an individual card being milled.
 * Stores the card at the moment it is milled.
 *
 * @author Susucr
 */
public class MilledCardEvent extends GameEvent {

    public MilledCardEvent(Card card, UUID playerId, Ability source) {
        super(EventType.MILLED_CARD, card.getId(), source, playerId);
    }

    public Card getCard(Game game) {
        return game.getCard(getTargetId());
    }
}
