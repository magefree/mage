
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class SurpriseManeuver extends CardImpl {

    public SurpriseManeuver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Untap up to two target creatures you control.
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(0, 2));
        this.getSpellAbility().addEffect(new UntapTargetEffect());

        // Creatures your opponents control get -1/-0 until end of turn.
        this.getSpellAbility().addEffect(new BoostOpponentsEffect(-1, 0, Duration.EndOfTurn));

    }

    private SurpriseManeuver(final SurpriseManeuver card) {
        super(card);
    }

    @Override
    public SurpriseManeuver copy() {
        return new SurpriseManeuver(this);
    }
}
