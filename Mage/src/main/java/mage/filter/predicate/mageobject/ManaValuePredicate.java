
package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.constants.ComparisonType;
import mage.filter.predicate.IntComparePredicate;

/**
 *
 * @author North
 */
public class ManaValuePredicate extends IntComparePredicate<MageObject> {

    public ManaValuePredicate(ComparisonType type, int value) {
        super(type, value);
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
