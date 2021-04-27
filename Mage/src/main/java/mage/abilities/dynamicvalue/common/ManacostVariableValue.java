package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.constants.AbilityType;
import mage.game.Game;
import mage.watchers.common.ManaSpentToCastWatcher;

public enum ManacostVariableValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        if (sourceAbility.getAbilityType() == AbilityType.SPELL) {
            return sourceAbility.getManaCostsToPay().getX();
        }
        ManaSpentToCastWatcher watcher = game.getState().getWatcher(ManaSpentToCastWatcher.class, sourceAbility.getSourceId());
        return watcher != null ? watcher.getAndResetLastXValue() : sourceAbility.getManaCostsToPay().getX();
    }

    @Override
    public ManacostVariableValue copy() {
        return ManacostVariableValue.instance;
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
