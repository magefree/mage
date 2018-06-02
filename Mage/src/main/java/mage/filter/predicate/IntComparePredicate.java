
package mage.filter.predicate;

import mage.MageObject;
import mage.constants.ComparisonType;
import mage.game.Game;

/**
 *
 * @author North
 * @param <T>
 */
public abstract class IntComparePredicate<T extends MageObject> implements Predicate<T> {

    protected final ComparisonType type;
    protected final int value;

    public IntComparePredicate(ComparisonType type, int value) {
        this.type = type;
        this.value = value;
    }

    protected abstract int getInputValue(T input);

    @Override
    public final boolean apply(T input, Game game) {
        int inputValue = getInputValue(input);
        return ComparisonType.compare(inputValue, type, value);
    }

    @Override
    public String toString() {
        return type.toString() + value;
    }
}
