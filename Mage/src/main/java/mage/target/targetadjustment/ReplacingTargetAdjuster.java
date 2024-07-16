package mage.target.targetadjustment;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.target.Target;

/**
 * @author notgreat
 */
public class ReplacingTargetAdjuster extends GenericTargetAdjuster {
    private final Condition condition;
    private final Target replacementTarget;

    /**
     * If the condition is true, uses the replacement target. Otherwise, use the original
     *
     * @param condition   The condition to be checked
     * @param replacementTarget The target to use if the condition is true
     */
    public ReplacingTargetAdjuster(Condition condition, Target replacementTarget) {
        this.condition = condition;
        this.replacementTarget = replacementTarget;
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        if (condition.apply(game, ability)){
            ability.addTarget(replacementTarget.copy());
        } else {
            ability.addTarget(blueprintTarget.copy());
        }
    }
}
