package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.DashAbility;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * @author LevelX2
 */
public enum DashedCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil.checkSourceCostsTagExists(game, source, DashAbility.getActivationKey());
    }
}
