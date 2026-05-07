
package mage.abilities.dynamicvalue;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.game.Game;

/**
 * @author Susucr
 */
public class IntMinusDynamicValue implements DynamicValue {

    private final DynamicValue value;
    private final int baseValue;

    public IntMinusDynamicValue(int baseValue, DynamicValue value) {
        this.value = value;
        this.baseValue = baseValue;
    }

    IntMinusDynamicValue(final IntMinusDynamicValue dynamicValue) {
        this.value = dynamicValue.value;
        this.baseValue = dynamicValue.baseValue;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return baseValue - value.calculate(game, sourceAbility, effect);
    }

    @Override
    public IntMinusDynamicValue copy() {
        return new IntMinusDynamicValue(this);
    }

    @Override
    public String toString() {
        return baseValue + " minus " + value.toString();
    }

    @Override
    public String getMessage() {
        return baseValue + " minus " + value.getMessage();
    }

    @Override
    public int getSign() {
        return value.getSign();
    }
}
