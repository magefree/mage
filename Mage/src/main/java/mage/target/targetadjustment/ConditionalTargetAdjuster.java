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
    private Targets originalTargets;
    private Targets checkTargets = null;

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

    /**
     * Use a special set of targets for checking if the spell/ability is legal to cast/activate
     * @param targets
     */
    public ConditionalTargetAdjuster withSpecificCheckTargets(Target... targets){
        this.checkTargets = new Targets(targets);
        return this;
    }

    /**
     * Use the replacement targets for checking if the spell/ability is legal to cast/activate.
     * Since this is to be used only if the targets are more general, keepExistingTargets must be false.
     */
    public ConditionalTargetAdjuster withCheckTargets() {
        if (this.keepExistingTargets) {
            throw new IllegalStateException("withCheckTargets requires keepExistingTargets be false (consider withSpecificCheckTargets)");
        }
        this.checkTargets = this.replacementTargets;
        return this;
    }

    @Override
    public void addDefaultTargets(Ability ability) {
        if (originalTargets == null) {
            originalTargets = ability.getTargets().copy();
        } else {
            throw new IllegalStateException("Wrong code usage: target adjuster already has blueprint target - " + originalTargets);
        }
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        boolean check = condition.apply(game, ability);
        ability.getTargets().clear();
        if (!check || keepExistingTargets) {
            for (Target target : originalTargets) {
                ability.addTarget(target.copy());
            }
        }
        if (check) {
            for (Target target : replacementTargets) {
                ability.addTarget(target.copy());
            }
        }
    }

    public void adjustTargetsCheck(Ability ability, Game game){
        if (checkTargets == null) {
            adjustTargets(ability, game); // use the normal targets
        } else {
            ability.getTargets().clear(); // use the special check targets
            for (Target target : checkTargets) {
                ability.addTarget(target.copy());
            }
        }
    }
}
