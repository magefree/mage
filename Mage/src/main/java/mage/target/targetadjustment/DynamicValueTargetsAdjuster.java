package mage.target.targetadjustment;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.game.Game;
import mage.target.Target;

/**
 *
 * @author TheElk801, notgreat
 */
public class DynamicValueTargetsAdjuster implements TargetAdjuster {
    private Target blueprintTarget = null;
    private final DynamicValue dynamicValue;

    public DynamicValueTargetsAdjuster(DynamicValue value) {
        this.dynamicValue = value;
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (blueprintTarget == null) {
            blueprintTarget = ability.getTargets().get(0).copy();
        }
        Target newTarget = blueprintTarget.copy();
        int count = dynamicValue.calculate(game, ability, null);
        newTarget.setMaxNumberOfTargets(count);
        if (blueprintTarget.getMinNumberOfTargets() != 0) {
            newTarget.setMinNumberOfTargets(count);
        }
        ability.getTargets().clear();
        ability.addTarget(newTarget);
    }
}
