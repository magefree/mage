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
    private final Targets replacementTargets;

    /**
     * If the condition is true, replace the target
     *
     * @param condition   The condition to be checked
     * @param replacementTarget The target to use if the condition is true. Can also take a Targets list
     * */
    public ConditionalTargetAdjuster(Condition condition, Target replacementTarget) {
        this(condition, new Targets(replacementTarget));
    }
    public ConditionalTargetAdjuster(Condition condition, Targets replacementTargets) {
        this.condition = condition;
        this.replacementTargets = replacementTargets;
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (condition.apply(game, ability)){
            ability.getTargets().clear();
            for(Target target :replacementTargets) {
                ability.addTarget(target.copy());
            }
        }
    }
}
