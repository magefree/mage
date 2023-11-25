

package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.EvokeAbility;
import mage.game.Game;
import mage.util.CardUtil;

/**
 *  Checks if a the spell was cast with the alternate evoke costs
 *
 * @author LevelX2
 */

public enum EvokedCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil.checkSourceCostsTagExists(game, source, EvokeAbility.getActivationKey());
    }
}
