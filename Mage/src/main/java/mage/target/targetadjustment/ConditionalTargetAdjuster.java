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
    private final boolean keepBlueprintTarget;
    private final Target replacementTarget;
    private Target blueprintTarget;

    /**
     * If the condition is true, replace the ability's target.
     * <p>
     * Note that if the conditional target can be found when the original can not,
     * you must use the two-target constructor and give the ability a separate general target
     * that can encompass both targets
     *
     * @param condition         The condition to be checked
     * @param replacementTarget The target to use if the condition is true.
     */
    public ConditionalTargetAdjuster(Condition condition, Target replacementTarget) {
        this(condition, null, false, replacementTarget);
    }

    /**
     * If the condition is true, add another target to the ability
     *
     * @param condition           The condition to be checked
     * @param keepBlueprintTarget if true, don't remove the original target when adding the new one
     * @param replacementTarget   The target to use if the condition is true.
     */
    public ConditionalTargetAdjuster(Condition condition, boolean keepBlueprintTarget, Target replacementTarget) {
        this(condition, null, keepBlueprintTarget, replacementTarget);
    }

    /**
     * If the condition is false, use the blueprint. If the condition is true, use the replacement target.
     *
     * @param condition         The condition to be checked
     * @param blueprintTarget   The blueprint/original target to use (set to null to use the ability's first target)
     * @param replacementTarget The target to use if the condition is true.
     */
    public ConditionalTargetAdjuster(Condition condition, Target blueprintTarget, Target replacementTarget) {
        this(condition, blueprintTarget, false, replacementTarget);
    }

    /**
     * If the condition is false, use the blueprint. If the condition is true, add or use the replacement target.
     *
     * @param condition           The condition to be checked
     * @param blueprintTarget     The blueprint/original target to use (set to null to use the ability's first target)
     * @param keepBlueprintTarget if true, don't remove the original target when adding the new one
     * @param replacementTarget   Target to be added if the condition is true
     */
    public ConditionalTargetAdjuster(Condition condition, Target blueprintTarget, boolean keepBlueprintTarget, Target replacementTarget) {
        this.condition = condition;
        this.keepBlueprintTarget = keepBlueprintTarget;
        this.blueprintTarget = blueprintTarget;
        this.replacementTarget = replacementTarget;
    }

    @Override
    public void addDefaultTargets(Ability ability) {
        if (blueprintTarget == null && !ability.getTargets().isEmpty()) {
            blueprintTarget = ability.getTargets().get(0).copy();
        }
    }
    @Override
    public void clearDefaultTargets() {
        blueprintTarget = null;
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        boolean result = condition.apply(game, ability);
        if (keepBlueprintTarget || !result) {
            if (blueprintTarget != null) {
                ability.addTarget(blueprintTarget.copy());
            }
        }
        if (result) {
            ability.addTarget(replacementTarget.copy());
        }
    }
}
