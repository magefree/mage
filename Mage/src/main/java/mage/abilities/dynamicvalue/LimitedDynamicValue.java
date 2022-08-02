package mage.abilities.dynamicvalue;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public class LimitedDynamicValue implements DynamicValue {

    private final DynamicValue value;
    private final int limit;

    /**
     * Returns a dynamic but with an upper limit.
     *
     * @param limit the max value the dynamic value will return
     * @param value the dynamic value to calculate the row dynamic value
     */
    public LimitedDynamicValue(int limit, DynamicValue value) {
        this.value = value;
        this.limit = limit;
    }

    LimitedDynamicValue(final LimitedDynamicValue dynamicValue) {
        this.value = dynamicValue.value;
        this.limit = dynamicValue.limit;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Math.min(limit, value.calculate(game, sourceAbility, effect));
    }

    @Override
    public LimitedDynamicValue copy() {
        return new LimitedDynamicValue(this);
    }

    @Override
    public String toString() {
        return value.toString();
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
