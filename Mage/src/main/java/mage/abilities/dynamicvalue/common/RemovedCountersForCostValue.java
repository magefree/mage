package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * @author LevelX2
 */
public enum RemovedCountersForCostValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return CardUtil.getSourceCostsTag(game, sourceAbility, "X", 0);
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public RemovedCountersForCostValue copy() {
        return RemovedCountersForCostValue.instance;
    }

    @Override
    public String toString() {
        return "X";
    }
}
