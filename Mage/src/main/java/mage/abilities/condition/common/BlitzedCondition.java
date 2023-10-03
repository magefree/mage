package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.BlitzAbility;
import mage.game.Game;
import mage.util.CardUtil;


/**
 * @author notgreat
 */
public enum BlitzedCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil.getSourceCostTags(game, source).containsKey(BlitzAbility.BLITZ_ACTIVATION_VALUE_KEY);
    }
}
