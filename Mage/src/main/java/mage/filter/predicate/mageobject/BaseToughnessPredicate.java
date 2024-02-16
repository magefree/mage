package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.constants.ComparisonType;
import mage.filter.predicate.IntComparePredicate;

public class BaseToughnessPredicate extends IntComparePredicate<MageObject> {

    public BaseToughnessPredicate(ComparisonType type, int value) {
        super(type, value);
    }

    @Override
    protected int getInputValue(MageObject input) {
        return input.getToughness().getModifiedBaseValue();
    }

    @Override
    public String toString() {
        return "Base toughness" + super.toString();
    }
}

