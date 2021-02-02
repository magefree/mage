
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author Styxo
 */
public final class RallyingFire extends CardImpl {

    public RallyingFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}");

        // Creatures you control get +2/+0 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(2, 0, Duration.EndOfTurn));

    }

    private RallyingFire(final RallyingFire card) {
        super(card);
    }

    @Override
    public RallyingFire copy() {
        return new RallyingFire(this);
    }
}
