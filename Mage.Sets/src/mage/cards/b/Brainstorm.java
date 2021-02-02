
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.BrainstormEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author jonubuu
 */
public final class Brainstorm extends CardImpl {

    public Brainstorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Draw three cards, then put two cards from your hand on top of your library in any order.
        this.getSpellAbility().addEffect(new BrainstormEffect());
    }

    private Brainstorm(final Brainstorm card) {
        super(card);
    }

    @Override
    public Brainstorm copy() {
        return new Brainstorm(this);
    }
}
