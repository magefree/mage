
package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;

/**
 *
 * @author Quercitron
 */
public class HalfValue implements DynamicValue {

    private final DynamicValue value;
    private final boolean roundedUp;

    public HalfValue(final DynamicValue value, final boolean roundedUp) {
        this.value = value.copy();
        this.roundedUp = roundedUp;
    }

    public HalfValue(final HalfValue halfValue) {
        this.value = halfValue.value.copy();
        this.roundedUp = halfValue.roundedUp;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return (value.calculate(game, sourceAbility, effect) + (roundedUp ? 1 : 0)) / 2;
    }

    @Override
    public HalfValue copy() {
        return new HalfValue(this);
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("half ").append(value.getMessage());
        if (roundedUp) {
            sb.append(", rounded up");
        } else {
            sb.append(", rounded down");
        }
        return sb.toString();
    }

    @Override
    public int getSign() {
        return value.getSign();
    }

    @Override
    public String toString() {
        return "half ";
    }
}
