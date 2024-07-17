package mage.target.targetadjustment;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.target.Target;

/**
 * @author notgreat
 */
public class ConditionalTargetAdjuster implements TargetAdjuster {
    private final Condition condition;
    private final Target replacementTarget;

    /**
     * If the condition is true, replace the target
     *
     * @param condition   The condition to be checked
     * @param replacementTarget The target to use if the condition is true
     */
    public ConditionalTargetAdjuster(Condition condition, Target replacementTarget) {
        this.condition = condition;
        this.replacementTarget = replacementTarget;
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (condition.apply(game, ability)){
            ability.getTargets().clear();
            ability.addTarget(replacementTarget.copy());
        }
    }
}
