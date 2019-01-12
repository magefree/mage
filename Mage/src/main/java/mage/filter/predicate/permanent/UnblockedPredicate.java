
package mage.filter.predicate.permanent;

import mage.constants.PhaseStep;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.game.turn.Step;

/**
 * Checks if an attacking creature is unblocked after the declare blockers step.
 *
 * @author LevelX2
 */
public enum UnblockedPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        if (input.isAttacking()) {
            if ((game.getPhase().getStep().getType() == PhaseStep.DECLARE_BLOCKERS
                    && game.getStep().getStepPart() == Step.StepPart.PRIORITY)
                    || game.getPhase().getStep().getType() == PhaseStep.FIRST_COMBAT_DAMAGE
                    || game.getPhase().getStep().getType() == PhaseStep.COMBAT_DAMAGE
                    || game.getPhase().getStep().getType() == PhaseStep.END_COMBAT) {
                CombatGroup combatGroup = game.getCombat().findGroup(input.getId());
                if (combatGroup != null) {
                    return !combatGroup.getBlocked();
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Unblocked";
    }
}
