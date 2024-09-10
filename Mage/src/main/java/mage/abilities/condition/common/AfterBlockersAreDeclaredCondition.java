package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.PhaseStep;
import mage.game.Game;

/**
 * This condtion does not check that the turnPhase is combat. You have to check
 * this if needed on another place.
 *
 * @author LevelX2
 */
public enum AfterBlockersAreDeclaredCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {

        return !(game.getTurnStepType() == PhaseStep.BEGIN_COMBAT
                || game.getTurnStepType() == PhaseStep.DECLARE_ATTACKERS);
    }

    @Override
    public String toString() {
        return "after blockers are declared";
    }
}
