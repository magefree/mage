package mage.target.targetadjustment;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.constants.ComparisonType;
import mage.filter.Filter;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.target.Target;

/**
 * @author TheElk801, notgreat
 */
public class MVTargetAdjuster implements TargetAdjuster {
    private Target blueprintTarget = null;
    private final DynamicValue dynamicValue;
    private final ComparisonType comparison;

    /**
     * Modifies the target to also require a mana value that satisfies the comparison to the dynamic value.
     *
     * @param value   The value to be compared against
     * @param compare Which comparison to use
     */
    public MVTargetAdjuster(DynamicValue value, ComparisonType compare) {
        this.dynamicValue = value;
        this.comparison = compare;
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (blueprintTarget == null) {
            blueprintTarget = ability.getTargets().get(0).copy();
            blueprintTarget.clearChosen();
        }
        Target newTarget = blueprintTarget.copy();
        int amount = dynamicValue.calculate(game, ability, null);
        Filter<MageObject> filter = newTarget.getFilter();
        filter.add(new ManaValuePredicate(comparison, amount));
        newTarget.setTargetName(filter.getMessage() + " (Mana Value " + comparison + " " + amount + ")");
        ability.getTargets().clear();
        ability.addTarget(newTarget);
    }
}
