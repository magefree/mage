

package mage.cards.f;

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


public final class FatalFumes extends CardImpl {

    public FatalFumes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{B}");


        // Target creature gets -4/-2 until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(-4,-2, Duration.EndOfTurn));
    }

    private FatalFumes(final FatalFumes card) {
        super(card);
    }

    @Override
    public FatalFumes copy() {
        return new FatalFumes(this);
    }

}
