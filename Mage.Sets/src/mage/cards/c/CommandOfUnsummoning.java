
package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.condition.common.AttackedThisStepCondition;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.TurnPhase;
import mage.target.common.TargetAttackingCreature;
import mage.watchers.common.PlayerAttackedStepWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CommandOfUnsummoning extends CardImpl {

    public CommandOfUnsummoning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Cast Command of Unsummoning only during the declare attackers step and only if you've been attacked this step.
        Ability ability = new CastOnlyDuringPhaseStepSourceAbility(
                TurnPhase.COMBAT, PhaseStep.DECLARE_ATTACKERS, AttackedThisStepCondition.instance,
                "Cast this spell only during the declare attackers step and only if you've been attacked this step."
        );
        ability.addWatcher(new PlayerAttackedStepWatcher());
        this.addAbility(ability);

        // Return one or two target attacking creatures to their owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect().setText("Return one or two target attacking creatures to their owner's hand."));
        this.getSpellAbility().addTarget(new TargetAttackingCreature(1, 2));
    }

    private CommandOfUnsummoning(final CommandOfUnsummoning card) {
        super(card);
    }

    @Override
    public CommandOfUnsummoning copy() {
        return new CommandOfUnsummoning(this);
    }
}
