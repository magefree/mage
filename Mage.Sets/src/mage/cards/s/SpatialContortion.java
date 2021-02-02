
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class SpatialContortion extends CardImpl {

    public SpatialContortion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{C}");

        // Target creature gets +3/-3 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, -3, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SpatialContortion(final SpatialContortion card) {
        super(card);
    }

    @Override
    public SpatialContortion copy() {
        return new SpatialContortion(this);
    }
}
