package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;

import java.util.HashMap;
import java.util.Map;

public class StaticValue implements DynamicValue {

    private static final Map<Integer, StaticValue> staticValueMap = new HashMap();

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

    public int getValue() {
        return value;
    }

    public static StaticValue get(int value) {
        staticValueMap.putIfAbsent(value, new StaticValue(value));
        return staticValueMap.get(value);
    }
}
