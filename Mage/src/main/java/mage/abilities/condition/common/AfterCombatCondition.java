
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.PhaseStep;
import mage.game.Game;

/**
 * @author L_J
 */
public enum AfterCombatCondition implements Condition {

    instance;
    @Override

    public boolean apply(Game game, Ability source) {
        return game.getTurnStepType().isAfter(PhaseStep.END_COMBAT);
    }

    @Override
    public String toString() {
        return "after combat";
    }
}
