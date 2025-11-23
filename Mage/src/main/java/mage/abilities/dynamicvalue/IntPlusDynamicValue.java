
package mage.abilities.dynamicvalue;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public class IntPlusDynamicValue implements DynamicValue {

    private final DynamicValue value;
    private final int baseValue;

    public IntPlusDynamicValue(int baseValue, DynamicValue value) {
        this.value = value;
        this.baseValue = baseValue;
    }

    IntPlusDynamicValue(final IntPlusDynamicValue dynamicValue) {
        this.value = dynamicValue.value;
        this.baseValue = dynamicValue.baseValue;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return baseValue + value.calculate(game, sourceAbility, effect);
    }

    @Override
    public IntPlusDynamicValue copy() {
        return new IntPlusDynamicValue(this);
    }

    @Override
    public String toString() {
        return baseValue + " plus " + value.toString();
    }

    @Override
    public String getMessage() {
        return baseValue + " plus " + value.getMessage();
    }

    @Override
    public int getSign() {
        return value.getSign();
    }
}
