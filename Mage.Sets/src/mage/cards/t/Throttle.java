
package mage.cards.t;

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
public final class Throttle extends CardImpl {

    public Throttle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{B}");


        // Target creature gets -4/-4 until end of turn
        this.getSpellAbility().addEffect(new BoostTargetEffect(-4,-4, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Throttle(final Throttle card) {
        super(card);
    }

    @Override
    public Throttle copy() {
        return new Throttle(this);
    }
}
