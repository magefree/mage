package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.watchers.common.EnergySpentOrLostWatcher;

public enum EnergySpentOrLostThisTurnCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return EnergySpentOrLostWatcher.getAmountEnergyLostOrSpentThisTurn(game, sourceAbility.getControllerId());
    }

    @Override
    public EnergySpentOrLostThisTurnCount copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "{E} you've paid or lost this turn";
    }

    @Override
    public String toString() {
        return "X";
    }
}
