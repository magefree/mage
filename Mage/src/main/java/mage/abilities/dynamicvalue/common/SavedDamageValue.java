package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;

/**
 * @author TheElk801
 */
public enum SavedDamageValue implements DynamicValue {
    MANY("many"),
    MUCH("much");

    private final String message;

    SavedDamageValue(String message) {
        this.message = message;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return (Integer) effect.getValue("damage");
    }

    @Override
    public SavedDamageValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "that " + message;
    }

    @Override
    public String getMessage() {
        return "";
    }
}
