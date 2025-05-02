package mage.game.events;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public class ExploredEvent extends GameEvent {

    private final Card card;

    public ExploredEvent(Permanent permanent, Ability source, Card card) {
        super(EventType.EXPLORED, permanent.getId(), source, permanent.getControllerId());
        this.card = card;
    }

    public Card getCard() {
        return card;
    }
}
