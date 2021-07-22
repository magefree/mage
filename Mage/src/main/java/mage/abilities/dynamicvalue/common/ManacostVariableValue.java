package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.watchers.common.ManaSpentToCastWatcher;

public enum ManacostVariableValue implements DynamicValue {

    REGULAR, // if you need X on cast/activate (in stack)
    ETB; // if you need X after ETB (in battlefield)

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        if (this == REGULAR) {
            return sourceAbility.getManaCostsToPay().getX();
        }
        ManaSpentToCastWatcher watcher = game.getState().getWatcher(ManaSpentToCastWatcher.class);
        if (watcher != null) {
            return watcher.getAndResetLastXValue(sourceAbility);
        }
        return 0;
    }

    @Override
    public ManacostVariableValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "";
    }
}
