
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.SpectacleAbility;
import mage.constants.AbilityType;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public enum SpectacleCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getAbilityType() == AbilityType.TRIGGERED) {
            return CardUtil.getSourceCostTags(game, source).containsKey(SpectacleAbility.SPECTACLE_ACTIVATION_VALUE_KEY);
        } else {
            return source instanceof SpectacleAbility;
        }
    }
}
