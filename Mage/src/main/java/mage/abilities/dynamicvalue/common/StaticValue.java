package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class StaticValue extends DynamicValue {

    private static final List<StaticValue> staticValues = new ArrayList();

    static {
        IntStream.rangeClosed(-10, 10)
                .mapToObj(StaticValue::new)
                .forEachOrdered(staticValues::add);
    }

    private StaticValue(int value) {
        super(value);
    }

    @Override
    public int calculateBase(Game game, Ability sourceAbility, Effect effect) {
        return multiplier;
    }

    @Override
    public StaticValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return Integer.toString(multiplier);
    }

    @Override
    public String getSingleMessage() {
        return "";
    }

    @Override
    public String getPluralMessage() {
        return "";
    }

    public int getValue() {
        return multiplier;
    }

    public static StaticValue get(int value) {
        if (value < -10 || value > 10) {
            return new StaticValue(value);
        }
        return staticValues.get(value + 10);
    }
}
