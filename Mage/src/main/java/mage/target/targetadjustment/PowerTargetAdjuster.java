package mage.target.targetadjustment;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.constants.ComparisonType;
import mage.filter.Filter;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.target.Target;

/**
 * @author TheElk801, notgreat
 */
public class PowerTargetAdjuster extends GenericTargetAdjuster {
    private final DynamicValue dynamicValue;
    private final ComparisonType comparison;

    /**
     * Modifies the target to also require a power that satisfies the comparison to the dynamic value.
     *
     * @param value   The value to be compared against
     * @param compare Which comparison to use
     */
    public PowerTargetAdjuster(DynamicValue value, ComparisonType compare) {
        this.dynamicValue = value;
        this.comparison = compare;
    }

    public PowerTargetAdjuster(ComparisonType comparison) {
        this(ManacostVariableValue.REGULAR, comparison);
    }


    @Override
    public void adjustTargets(Ability ability, Game game) {
        Target newTarget = blueprintTarget.copy();
        int amount = dynamicValue.calculate(game, ability, ability.getEffects().get(0));
        Filter<MageObject> filter = newTarget.getFilter();
        filter.add(new PowerPredicate(comparison, amount));
        newTarget.withTargetName(filter.getMessage() + " (Power " + comparison + " " + amount + ")");
        ability.getTargets().clear();
        ability.addTarget(newTarget);
    }
}
