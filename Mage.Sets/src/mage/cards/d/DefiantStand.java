
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.condition.common.AttackedThisStepCondition;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.PhaseStep;
import mage.constants.TurnPhase;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.PlayerAttackedStepWatcher;

/**
 *
 * @author TheElk801
 */
public final class DefiantStand extends CardImpl {

    public DefiantStand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Cast Defiant Stand only during the declare attackers step and only if you've been attacked this step.
        Ability ability = new CastOnlyDuringPhaseStepSourceAbility(
                TurnPhase.COMBAT, PhaseStep.DECLARE_ATTACKERS, AttackedThisStepCondition.instance,
                "Cast this spell only during the declare attackers step and only if you've been attacked this step."
        );
        ability.addWatcher(new PlayerAttackedStepWatcher());
        this.addAbility(ability);

        // Target creature gets +1/+3 until end of turn. Untap that creature.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 3, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap that creature"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private DefiantStand(final DefiantStand card) {
        super(card);
    }

    @Override
    public DefiantStand copy() {
        return new DefiantStand(this);
    }
}
