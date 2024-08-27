package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.GiftAbility;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public enum GiftWasPromisedCondition implements Condition {
    TRUE(true),
    FALSE(false);
    private final boolean flag;

    GiftWasPromisedCondition(boolean flag) {
        this.flag = flag;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return flag == CardUtil.checkSourceCostsTagExists(game, source, GiftAbility.GIFT_ACTIVATION_VALUE_KEY);
    }

    @Override
    public String toString() {
        return "the gift was " + (flag ? "" : "not ") + "promised";
    }
}
