
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class AlleyEvasion extends CardImpl {

    public AlleyEvasion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Choose one
        // Target creature you control gets +1/+2 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 2, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        // Return target creature you control to its owner's hand.
        Mode mode = new Mode(new ReturnToHandTargetEffect());
        mode.addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addMode(mode);
    }

    private AlleyEvasion(final AlleyEvasion card) {
        super(card);
    }

    @Override
    public AlleyEvasion copy() {
        return new AlleyEvasion(this);
    }
}
