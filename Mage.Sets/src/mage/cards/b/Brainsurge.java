
package mage.cards.b;

import mage.abilities.effects.common.BrainstormEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class Brainsurge extends CardImpl {

    public Brainsurge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Draw four cards, then put two cards from your hand on top of your library in any order.
        this.getSpellAbility().addEffect(new BrainstormEffect(4, 2));
    }

    private Brainsurge(final Brainsurge card) {
        super(card);
    }

    @Override
    public Brainsurge copy() {
        return new Brainsurge(this);
    }
}
