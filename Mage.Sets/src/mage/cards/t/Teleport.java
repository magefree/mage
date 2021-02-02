
package mage.cards.t;

import java.util.UUID;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class Teleport extends CardImpl {

    public Teleport(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{U}{U}");

        // Cast Teleport only during the declare attackers step.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(null, PhaseStep.DECLARE_ATTACKERS, null, "Cast Teleport only during the declare attackers step"));

        // Target creature can't be blocked this turn.
        this.getSpellAbility().addEffect(new CantBeBlockedTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Teleport(final Teleport card) {
        super(card);
    }

    @Override
    public Teleport copy() {
        return new Teleport(this);
    }
}
