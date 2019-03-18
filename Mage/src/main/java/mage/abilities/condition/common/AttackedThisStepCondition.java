
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.watchers.common.PlayerAttackedStepWatcher;

/**
 * @author LevelX2
 */
public enum AttackedThisStepCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        PlayerAttackedStepWatcher watcher = game.getState().getWatcher(PlayerAttackedStepWatcher.class);
        return watcher != null
                && watcher.getNumberAttackingCurrentStep(source.getControllerId()) > 0;
    }

    @Override
    public String toString() {
        return "during the declare attackers step and only if you've been attacked this step.";
    }
}
