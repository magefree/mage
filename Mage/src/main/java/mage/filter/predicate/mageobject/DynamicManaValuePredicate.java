package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.constants.ComparisonType;
import mage.filter.predicate.DynamicIntComparePredicate;

/**
 * Dynamic version of {@link ManaValuePredicate}
 * <p>
 * Upon instantiation, choose {@link ComparisonType} and {@link DynamicValue}
 * as well as context for the DynamicValue if applicable ({@link Ability} and {@link Effect})
 * <p>
 * The predicate will compare the mana cost of the input to the DynamicValue. The predicate's return value depends on
 * the chosen ComparisonType:
 * <ul>
 * <li>{@link ComparisonType#FEWER_THAN} - the predicate returns true if mana value is strictly less than (<) the dynamic value</li>
 * <li>{@link ComparisonType#EQUAL_TO} - the predicate returns true if mana value is equal to (==) the dynamic value</li>
 * <li>{@link ComparisonType#MORE_THAN} - the predicate returns true if mana value is strictly greater than (>) the dynamic value</li>
 * </ul>
 *
 * @author the-red-lily
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
