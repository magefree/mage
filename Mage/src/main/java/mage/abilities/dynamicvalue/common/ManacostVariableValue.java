package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.util.CardUtil;

public enum ManacostVariableValue implements DynamicValue {
    //TODO: all three of these variants plus GetXValue, GetKickerXValue, and GetXLoyaltyValue use the same logic
    // and should be consolidated into a single instance
    REGULAR, // if you need X on cast/activate (in stack) - reset each turn
    ETB, // if you need X after ETB (in battlefield) - reset each turn
    END_GAME; // if you need X until end game - keep data forever


    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return CardUtil.getSourceCostsTag(game, sourceAbility, "X", 0);
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
