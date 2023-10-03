package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.DashAbility;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * @author notgreat
 */
public enum DashedCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil.getSourceCostTags(game, source).containsKey(DashAbility.getActivationKey());
    }
}
