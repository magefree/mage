package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.util.CardUtil;

public enum ManacostVariableValue implements DynamicValue {
    REGULAR, // if you need X on cast/activate (in stack) - reset each turn
    ETB; // if you need X after ETB (in battlefield) - keep until turn end after leaving battlefield

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        if (this == REGULAR) {
            return sourceAbility.getManaCostsToPay().getX();
        }
        return (int)CardUtil.getSourceCostTags(game, sourceAbility).getOrDefault("X",0);
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
