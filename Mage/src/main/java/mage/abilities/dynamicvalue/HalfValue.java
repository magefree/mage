package mage.abilities.dynamicvalue;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.game.Game;

/**
 * @author Susucr
 */
public class HalfValue implements DynamicValue {

    private final DynamicValue value;
    private final boolean roundedUp; // rounded down if false.

    public static HalfValue roundedUp(DynamicValue value) {
        return new HalfValue(value, true);
    }

    public static HalfValue roundedDown(DynamicValue value) {
        return new HalfValue(value, false);
    }

    private HalfValue(DynamicValue value, boolean roundedUp) {
        this.value = value.copy();
        this.roundedUp = roundedUp;
    }

    private HalfValue(final HalfValue dynamicValue) {
        this.value = dynamicValue.value.copy();
        this.roundedUp = dynamicValue.roundedUp;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int base = value.calculate(game, sourceAbility, effect);
        if (base % 2 == 0) {
            return base / 2;
        } else {
            return 1 + base / 2;
        }
    }

    @Override
    public HalfValue copy() {
        return new HalfValue(this);
    }

    @Override
    public String toString() {
        return "half " + value.toString() + ", rounded " + (roundedUp ? "up" : "down");
    }

    @Override
    public String getMessage() {
        return value.getMessage();
    }

    @Override
    public int getSign() {
        return value.getSign();
    }
}
