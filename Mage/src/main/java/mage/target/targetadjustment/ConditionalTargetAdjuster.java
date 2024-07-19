package mage.target.targetadjustment;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.target.Target;
import mage.target.Targets;

/**
 * @author notgreat
 */
public class ConditionalTargetAdjuster implements TargetAdjuster {
    private final Condition condition;
    private final boolean keepOldTargets;
    private final Targets replacementTargets;

    /**
     * If the condition is true, replace the target
     *
     * @param condition         The condition to be checked
     * @param replacementTarget The target to use if the condition is true.
     */
    public ConditionalTargetAdjuster(Condition condition, Target replacementTarget) {
        this(condition, false, new Targets(replacementTarget));
    }

    /**
     * If the condition is true, change the target list
     *
     * @param condition         The condition to be checked
     * @param keepOldTargets    Add to the targets list instead of replacing it entirely
     * @param replacementTarget The target to use if the condition is true. Can also take a Targets list
     */
    public ConditionalTargetAdjuster(Condition condition, boolean keepOldTargets, Target replacementTarget) {
        this(condition, keepOldTargets, new Targets(replacementTarget));
    }

    /**
     * If the condition is true, change the target list with multiple targets at once
     *
     * @param condition          The condition to be checked
     * @param keepOldTargets     Add to the targets list instead of replacing it entirely
     * @param replacementTargets The targets list to use if the condition is true.
     */
    public ConditionalTargetAdjuster(Condition condition, boolean keepOldTargets, Targets replacementTargets) {
        this.condition = condition;
        this.keepOldTargets = keepOldTargets;
        this.replacementTargets = replacementTargets;
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (condition.apply(game, ability)) {
            if (!keepOldTargets) {
                ability.getTargets().clear();
            }
            for (Target target : replacementTargets) {
                ability.addTarget(target.copy());
            }
        }
    }
}
