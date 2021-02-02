
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class BruteForce extends CardImpl {

    public BruteForce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");


        // Target creature gets +3/+3 until end of turn.
        this.getSpellAbility().getEffects().add(new BoostTargetEffect(3,3, Duration.EndOfTurn));
        this.getSpellAbility().getTargets().add(new TargetCreaturePermanent());
    }

    private BruteForce(final BruteForce card) {
        super(card);
    }

    @Override
    public BruteForce copy() {
        return new BruteForce(this);
    }
}
