package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;

/**
 * @author Susucr
 */
public enum SavedCounterRemovedValue implements DynamicValue {
    MANY("many"),
    MUCH("much");

    private final String message;
    private static final String key = "CounterRemoved";

    public static String getValueKey() {
        return "CounterRemoved";
    }

    SavedCounterRemovedValue(String message) {
        this.message = "that " + message;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return (Integer) effect.getValue(getValueKey());
    }

    @Override
    public SavedCounterRemovedValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return message;
    }

    @Override
    public String getMessage() {
        return "";
    }
}
