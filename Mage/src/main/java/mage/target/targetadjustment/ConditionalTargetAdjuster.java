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
    private final boolean keepExistingTargets;
    private final Targets replacementTargets;

    /**
     * If the condition is true, replace the target
     *
     * @param condition         The condition to be checked
     * @param replacementTarget The target to use if the condition is true.
     */
    public ConditionalTargetAdjuster(Condition condition, Target replacementTarget) {
        this(condition, false, replacementTarget);
    }

    /**
     * If the condition is true, change the target list with multiple targets at once
     *
     * @param condition            The condition to be checked
     * @param keepExistingTargets  if true, don't clear existing targets when adding the new ones
     * @param replacementTargets   Targets to be added if the condition is true
     */
    public ConditionalTargetAdjuster(Condition condition, boolean keepExistingTargets, Target... replacementTargets) {
        this.condition = condition;
        this.keepExistingTargets = keepExistingTargets;
        this.replacementTargets = new Targets(replacementTargets);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (condition.apply(game, ability)) {
            if (!keepExistingTargets) {
                ability.getTargets().clear();
            }
            for (Target target : replacementTargets) {
                ability.addTarget(target.copy());
            }
        }
    }
}
