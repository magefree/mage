
package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class MomentOfCraving extends CardImpl {

    public MomentOfCraving(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{B}");

        // Target creature gets -2/-2 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-2, -2, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // You gain 2 life.
        this.getSpellAbility().addEffect(new GainLifeEffect(2));
    }

    private MomentOfCraving(final MomentOfCraving card) {
        super(card);
    }

    @Override
    public MomentOfCraving copy() {
        return new MomentOfCraving(this);
    }
}
