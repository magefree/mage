package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;

/**
 * @author TheElk801
 */
public enum SavedDiscardValue implements DynamicValue {
    MANY("many"),
    MUCH("much");

    private final String message;

    SavedDiscardValue(String message) {
        this.message = "that " + message;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return (Integer) effect.getValue("discarded");
    }

    @Override
    public SavedDiscardValue copy() {
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
