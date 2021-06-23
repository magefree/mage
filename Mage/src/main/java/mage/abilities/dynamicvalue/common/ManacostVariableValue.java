package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.watchers.common.ManaSpentToCastWatcher;

public enum ManacostVariableValue implements DynamicValue {
    REGULAR, ETB;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        if (this == REGULAR) {
            return sourceAbility.getManaCostsToPay().getX();
        }
        ManaSpentToCastWatcher watcher = game.getState().getWatcher(ManaSpentToCastWatcher.class);
        return watcher != null ? watcher.getAndResetLastXValue(sourceAbility.getSourceId()) : sourceAbility.getManaCostsToPay().getX();
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
