
 
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.PhaseStep;
import mage.constants.TurnPhase;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.turn.Step;

import java.util.UUID;

/**
 *
 * @author spjspj
 */

public class SourceTappedBeforeUntapStepCondition implements Condition {

    UUID permanentId = null;
    boolean permanentWasTappedBeforeUntapStep = false;
    int lastTurnNum = -1;
    


    public void setPermanentId(UUID permanentId) {
        this.permanentId = permanentId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        TurnPhase turnPhase = game.getTurn().getPhase().getType();
        Step step = game.getPhase().getStep();
        Permanent permanent = game.getBattlefield().getPermanent(permanentId);

        if (permanent != null) {
            if (lastTurnNum != game.getTurnNum() && turnPhase == TurnPhase.BEGINNING) {
                lastTurnNum = game.getTurnNum();
                permanentWasTappedBeforeUntapStep = permanent.isTapped();
            }

            if (step.getType() == PhaseStep.UNTAP) {
                return permanentWasTappedBeforeUntapStep;
            } else {
                return permanent.isTapped();
            }
        }
        return false;
    }
}
