package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class StaticValue implements DynamicValue {

    private static final List<StaticValue> staticValues = new ArrayList();

    static {
        IntStream.rangeClosed(-10, 10)
                .mapToObj(StaticValue::new)
                .forEachOrdered(staticValues::add);
    }

    private final int value;

    private StaticValue(int value) {
        this.value = value;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return value;
    }

    @Override
    public StaticValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public int getSign() {
        return value;
    }

    public int getValue() {
        return value;
    }

    public static StaticValue get(int value) {
        if (value < -10 || value > 10) {
            return new StaticValue(value);
        }
        return staticValues.get(value + 10);
    }
}
