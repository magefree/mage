
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.SurgeAbility;
import mage.constants.AbilityType;
import mage.game.Game;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LevelX2
 */
public enum SurgedCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getAbilityType() == AbilityType.TRIGGERED) {
            @SuppressWarnings("unchecked")
            List<Integer> surgeActivations = (ArrayList) game.getState().getValue(SurgeAbility.SURGE_ACTIVATION_VALUE_KEY + source.getSourceId());
            if (surgeActivations != null) {
                return surgeActivations.contains(game.getState().getZoneChangeCounter(source.getSourceId()) - 1);
            }
            return false;
        } else {
            return source instanceof SurgeAbility;
        }
    }
}
