
package mage.abilities.dynamicvalue;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public class MultipliedValue implements DynamicValue {

    private final DynamicValue value;
    private final int multplier; // should be renamed to multiplier but don't want to break your stuff

    public MultipliedValue(DynamicValue value, int multiplier) {
        this.value = value.copy();
        this.multplier = multiplier;
    }

    MultipliedValue(final MultipliedValue dynamicValue) {
        this.value = dynamicValue.value.copy();
        this.multplier = dynamicValue.multplier;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return multplier * value.calculate(game, sourceAbility, effect);
    }

    @Override
    public MultipliedValue copy() {
        return new MultipliedValue(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (multplier == 2) {
            sb.append("twice ");
        } else {
            sb.append(multplier).append(" * ");
        }
        return sb.append(value.toString()).toString();
    }

    @Override
    public String getMessage() {
        return value.getMessage();
    }
}
