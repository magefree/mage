package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.util.CardUtil;
import mage.watchers.common.ManaSpentToCastWatcher;

import java.util.Map;

public enum ManacostVariableValue implements DynamicValue {
    REGULAR, // if you need X on cast/activate (in stack) - reset each turn
    ETB; // if you need X after ETB (in battlefield) - keep until end of turn

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        if (this == REGULAR) {
            return sourceAbility.getManaCostsToPay().getX();
        }
        Map<String, Integer> map = CardUtil.getSourceCostTags(game, sourceAbility);
        return map.getOrDefault("X",0);
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
