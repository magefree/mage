
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author LoneFox

 */
public final class ShieldWall extends CardImpl {

    public ShieldWall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // Creatures you control get +0/+2 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(0, 2, Duration.EndOfTurn));
    }

    private ShieldWall(final ShieldWall card) {
        super(card);
    }

    @Override
    public ShieldWall copy() {
        return new ShieldWall(this);
    }
}
