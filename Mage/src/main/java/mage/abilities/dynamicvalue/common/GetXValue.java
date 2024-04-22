package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * @author BetaSteward_at_googlemail.com
 */
public enum GetXValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return CardUtil.getSourceCostsTag(game, sourceAbility, "X", 0);
    }

    @Override
    public GetXValue copy() {
        return GetXValue.instance;
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
