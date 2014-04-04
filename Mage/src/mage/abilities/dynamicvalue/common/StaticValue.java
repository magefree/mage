package mage.abilities.dynamicvalue.common;


import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.game.Game;

public class StaticValue implements DynamicValue {
    private int value = 0;
    private String message;
    
    public StaticValue(int value) {
        this(value, "");
    }
    public StaticValue(int value, String message) {
        this.value = value;
        this.message = message;
    }

    public StaticValue(final StaticValue staticValue) {
        this.value = staticValue.value;
        this.message = staticValue.message;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        return value;
    }

    @Override
    public DynamicValue copy() {
        return new StaticValue(this);
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
