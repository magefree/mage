
package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.constants.ComparisonType;
import mage.filter.predicate.IntComparePredicate;

/**
 *
 * @author North
 */
public class ConvertedManaCostPredicate extends IntComparePredicate<MageObject> {

    public ConvertedManaCostPredicate(ComparisonType type, int value) {
        super(type, value);
    }

    @Override
    protected int getInputValue(MageObject input) {
        return input.getConvertedManaCost();
    }

    @Override
    public String toString() {
        return "ConvertedManaCost" + super.toString();
    }
}
