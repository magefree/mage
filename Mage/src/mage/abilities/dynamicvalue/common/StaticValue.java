package mage.abilities.dynamicvalue.common;


import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.game.Game;

public class StaticValue implements DynamicValue {
    private int value = 0;

    public StaticValue(int value) {
        this.value = value;
    }

    public StaticValue(final StaticValue staticValue) {
        this.value = staticValue.value;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        return value;
    }

    @Override
    public DynamicValue clone() {
        return new StaticValue(this);
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public String getMessage() {
        return "";
    }
}
