
package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.constants.ComparisonType;
import mage.filter.predicate.DynamicIntComparePredicate;
import mage.filter.predicate.IntComparePredicate;

/**
 *
 * @author North
 */
public class DynamicManaValuePredicate extends DynamicIntComparePredicate<MageObject> {

    public DynamicManaValuePredicate(ComparisonType type, DynamicValue dynamicValue, Ability sourceAbility, Effect effect) {
        super(type, dynamicValue, sourceAbility, effect);
    }

    @Override
    protected int getInputValue(MageObject input) {
        return input.getManaValue();
    }

    @Override
    public String toString() {
        return "ManaValue" + super.toString();
    }
}
