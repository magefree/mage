
package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.constants.ComparisonType;
import mage.filter.predicate.IntComparePredicate;

/**
 *
 * @author North
 */
public class ToughnessPredicate extends IntComparePredicate<MageObject> {

    public ToughnessPredicate(ComparisonType type, int value) {
        super(type, value);
    }

    @Override
    protected int getInputValue(MageObject input) {
        return input.getToughness().getValue();
    }

    @Override
    public String toString() {
        return "Toughness" + super.toString();
    }
}
