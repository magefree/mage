package mage.target.targetadjustment;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.filter.Filter;
import mage.game.Game;
import mage.target.Target;

/**
 * @author TheElk801, notgreat
 */
public class TargetsCountAdjuster implements TargetAdjuster {
    private Target blueprintTarget = null;
    private final DynamicValue dynamicValue;

    /**
     * Modifies the target to be X targets, where X is the dynamic value.
     * If the ability's target has min targets of 0, it's treated as "up to X targets".
     * Otherwise, it's exactly "X targets".
     *
     * @param value The number of targets
     */
    public TargetsCountAdjuster(DynamicValue value) {
        this.dynamicValue = value;
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (blueprintTarget == null) {
            blueprintTarget = ability.getTargets().get(0).copy();
            blueprintTarget.clearChosen();
        }
        Target newTarget = blueprintTarget.copy();
        int count = dynamicValue.calculate(game, ability, ability.getEffects().get(0));
        newTarget.setMaxNumberOfTargets(count);
        Filter filter = newTarget.getFilter();
        if (blueprintTarget.getMinNumberOfTargets() != 0) {
            newTarget.setMinNumberOfTargets(count);
            newTarget.withTargetName(filter.getMessage() + " (" + count + " targets)");
        } else {
            newTarget.withTargetName(filter.getMessage() + " (up to " + count + " targets)");
        }
        ability.getTargets().clear();
        if (count > 0) {
            ability.addTarget(newTarget);
        }
    }
}
