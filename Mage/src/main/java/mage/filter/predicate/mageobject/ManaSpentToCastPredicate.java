package mage.filter.predicate.mageobject;

import mage.constants.ComparisonType;
import mage.filter.predicate.IntComparePredicate;
import mage.game.stack.StackObject;

/**
 * @author TheElk801
 */
public class ManaSpentToCastPredicate extends IntComparePredicate<StackObject> {

    public ManaSpentToCastPredicate(ComparisonType type, int value) {
        super(type, value);
    }

    @Override
    protected int getInputValue(StackObject input) {
        return input.getStackAbility().getManaCostsToPay().getUsedManaToPay().count();
    }

    @Override
    public String toString() {
        return "ManaSpent" + super.toString();
    }
}
