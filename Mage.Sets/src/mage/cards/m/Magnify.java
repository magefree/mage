
package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author LoneFox
 */
public final class Magnify extends CardImpl {

    public Magnify(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");

        // All creatures get +1/+1 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(1, 1, Duration.EndOfTurn));
    }

    private Magnify(final Magnify card) {
        super(card);
    }

    @Override
    public Magnify copy() {
        return new Magnify(this);
    }
}
