
package mage.filter.predicate;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.constants.ComparisonType;
import mage.game.Game;

/**
 *
 * @author North
 * @param <T>
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
        return type.toString() + dynamicValue;
    }
}
