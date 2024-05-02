package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;

import java.util.Optional;

/**
 * @author Susucr
 */
public enum SavedMilledValue implements DynamicValue {
    MANY("many"),
    MUCH("much");

    private final String message;

    public static final String VALUE_KEY = "SavedMilled";

    SavedMilledValue(String message) {
        this.message = "that " + message;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional.ofNullable((Integer) effect.getValue(VALUE_KEY)).orElse(0);
    }

    @Override
    public SavedMilledValue copy() {
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
