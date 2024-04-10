package mage.target.targetadjustment;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.constants.ComparisonType;
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
        }
        Target newTarget = blueprintTarget.copy();
        int mv = dynamicValue.calculate(game, ability, null);
        newTarget.getFilter().add(new ManaValuePredicate(comparison, mv));
        ability.getTargets().clear();
        ability.addTarget(newTarget);
    }
}
