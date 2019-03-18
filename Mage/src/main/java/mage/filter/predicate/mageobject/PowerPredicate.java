
package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.constants.ComparisonType;
import mage.filter.predicate.IntComparePredicate;

/**
 *
 * @author North
 */
public class PowerPredicate extends IntComparePredicate<MageObject> {

    public PowerPredicate(ComparisonType type, int value) {
        super(type, value);
    }

    @Override
    protected int getInputValue(MageObject input) {
        return input.getPower().getValue();
    }

    @Override
    public String toString() {
        return "Power" + super.toString();
    }
}
