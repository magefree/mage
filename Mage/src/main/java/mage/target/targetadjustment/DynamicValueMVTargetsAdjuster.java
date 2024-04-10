package mage.target.targetadjustment;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.constants.ComparisonType;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.target.Target;

/**
 *
 * @author TheElk801, notgreat
 */
public class DynamicValueMVTargetsAdjuster implements TargetAdjuster {
    private Target blueprintTarget = null;
    private final DynamicValue dynamicValue;
    private final ComparisonType comparison;

    public DynamicValueMVTargetsAdjuster(DynamicValue value, ComparisonType compare) {
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
