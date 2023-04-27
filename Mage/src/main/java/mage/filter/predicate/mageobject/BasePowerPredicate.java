package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.constants.ComparisonType;
import mage.filter.predicate.IntComparePredicate;

/**
 * @author Alex-Vasile
 */
public class BasePowerPredicate extends IntComparePredicate<MageObject> {

    public BasePowerPredicate(ComparisonType type, int value) {
        super(type, value);
    }

    @Override
    protected int getInputValue(MageObject input) {
        return input.getPower().getModifiedBaseValue();
    }

    @Override
    public String toString() {
        return "Base power" + super.toString();
    }
}

