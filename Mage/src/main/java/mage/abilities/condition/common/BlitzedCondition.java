package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.BlitzAbility;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public enum BlitzedCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil.checkSourceCostsTagExists(game, source, BlitzAbility.BLITZ_ACTIVATION_VALUE_KEY);
    }
}
