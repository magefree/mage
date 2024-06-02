package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;

/**
 * @author Susucr
 */
public enum SavedLifeLossValue implements DynamicValue {
    MANY("many"),
    MUCH("much");

    private final String message;

    private static final String key = "SavedLifeLoss";

    /**
     * value key used to store the amount of life lost
     */
    public static String getValueKey() {
        return key;
    }

    SavedLifeLossValue(String message) {
        this.message = "that " + message;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return (Integer) effect.getValue(getValueKey());
    }

    @Override
    public SavedLifeLossValue copy() {
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
