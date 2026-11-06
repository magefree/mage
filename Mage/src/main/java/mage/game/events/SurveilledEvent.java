package mage.game.events;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;

import java.util.Set;
import java.util.UUID;

/**
 * @author brahle
 */
public class SurveilledEvent extends GameEvent {

    private final Cards surveilledToGraveyard = new CardsImpl();

    public SurveilledEvent(UUID playerId, Ability source, int amount, Set<Card> toGraveyard) {
        super(EventType.SURVEILED, playerId, source, playerId, amount, true);
        if (toGraveyard != null) {
            this.surveilledToGraveyard.addAllCards(toGraveyard);
        }
    }

    public Cards getSurveilledToGraveyard() {
        return surveilledToGraveyard;
    }
}
