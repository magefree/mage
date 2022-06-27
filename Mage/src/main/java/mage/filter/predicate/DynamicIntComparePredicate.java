package mage.filter.predicate;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.constants.ComparisonType;
import mage.game.Game;

/**
 * Dynamic version of {@link IntComparePredicate}
 * <p>
 * Extend this class to make predicates which compare input to a dynamic value
 * <p>
 * Upon instantiation of child class, choose {@link ComparisonType} and {@link DynamicValue}
 * as well as context for the DynamicValue if applicable ({@link Ability} and {@link Effect})
 * <p>
 * The predicate will compare value of the input (determined by {@link #getInputValue(T)} to the DynamicValue.
 * The predicate's return value depends on the chosen ComparisonType:
 * <ul>
 * <li>{@link ComparisonType#FEWER_THAN} - the predicate returns true if {@link #getInputValue(T)} is strictly less than (<) the dynamic value</li>
 * <li>{@link ComparisonType#EQUAL_TO} - the predicate returns true if {@link #getInputValue(T)} is equal to (==) the dynamic value</li>
 * <li>{@link ComparisonType#MORE_THAN} - the predicate returns true if {@link #getInputValue(T)} is strictly greater than (>) the dynamic value</li>
 * </ul>
 *
 * @param <T> type upon which the predicate will act
 * @author the-red-lily
 */
public abstract class DynamicIntComparePredicate<T extends MageObject> implements Predicate<T> {

    protected final ComparisonType type;
    protected final DynamicValue dynamicValue;
    protected final Ability sourceAbility;
    protected final Effect effect;

    public DynamicIntComparePredicate(ComparisonType type, DynamicValue dynamicValue, Ability sourceAbility, Effect effect) {
        this.type = type;
        this.dynamicValue = dynamicValue;
        this.sourceAbility = sourceAbility;
        this.effect = effect;
    }

    protected abstract int getInputValue(T input);

    @Override
    public final boolean apply(T input, Game game) {
        int inputValue = getInputValue(input);
        return ComparisonType.compare(inputValue, type, dynamicValue.calculate(game, sourceAbility, effect));
    }

    @Override
    public String toString() {
        return type.toString() + dynamicValue.getMessage();
    }
}
