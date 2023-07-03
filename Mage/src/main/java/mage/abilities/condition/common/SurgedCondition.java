
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.SurgeAbility;
import mage.constants.AbilityType;
import mage.game.Game;
import mage.util.CardUtil;


/**
 *
 * @author LevelX2
 */
public enum SurgedCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getAbilityType() == AbilityType.TRIGGERED) {
            return CardUtil.getSourceCostTags(game, source).containsKey(SurgeAbility.SURGE_ACTIVATION_VALUE_KEY);
        } else {
            return source instanceof SurgeAbility;
        }
    }
}
