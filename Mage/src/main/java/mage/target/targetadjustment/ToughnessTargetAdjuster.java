package mage.target.targetadjustment;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.constants.ComparisonType;
import mage.filter.Filter;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.game.Game;
import mage.target.Target;

/**
 * @author TheElk801, notgreat
 */
public class ToughnessTargetAdjuster extends GenericTargetAdjuster {
    private final DynamicValue dynamicValue;
    private final ComparisonType comparison;

    /**
     * Modifies the target to also require a toughness that satisfies the comparison to the dynamic value.
     *
     * @param value   The value to be compared against
     * @param compare Which comparison to use
     */
    public ToughnessTargetAdjuster(DynamicValue value, ComparisonType compare) {
        this.dynamicValue = value;
        this.comparison = compare;
    }

    public ToughnessTargetAdjuster(ComparisonType comparison) {
        this(GetXValue.instance, comparison);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        Target newTarget = blueprintTarget.copy();
        int amount = dynamicValue.calculate(game, ability, ability.getEffects().get(0));
        Filter<MageObject> filter = newTarget.getFilter();
        filter.add(new ToughnessPredicate(comparison, amount));
        newTarget.withTargetName(filter.getMessage() + " (Toughness " + comparison + " " + amount + ")");
        ability.getTargets().clear();
        ability.addTarget(newTarget);
    }
}
