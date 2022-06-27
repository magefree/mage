package mage.filter.predicate;

import mage.MageObject;
import mage.constants.ComparisonType;
import mage.game.Game;

/**
 * Extend this class to make predicates which compare input to a specified integer
 * <p>
 * Upon instantiation of child class, choose {@link ComparisonType} and integer
 * <p>
 * The predicate will compare value of the input (determined by {@link #getInputValue(T)} to the integer value
 * The predicate's return value depends on the chosen ComparisonType:
 * <ul>
 * <li>{@link ComparisonType#FEWER_THAN} - the predicate returns true if {@link #getInputValue(T)} is strictly less than (<) the value</li>
 * <li>{@link ComparisonType#EQUAL_TO} - the predicate returns true if {@link #getInputValue(T)} is equal to (==) the value</li>
 * <li>{@link ComparisonType#MORE_THAN} - the predicate returns true if {@link #getInputValue(T)} is strictly greater than (>) the value</li>
 * </ul>
 *
 * @param <T> type upon which the predicate will act
 * @author North
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
