
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.SpectacleAbility;
import mage.constants.AbilityType;
import mage.game.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TheElk801
 */
public enum SpectacleCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getAbilityType() == AbilityType.TRIGGERED) {
            @SuppressWarnings("unchecked")
            List<Integer> spectacleActivations = (ArrayList) game.getState().getValue(SpectacleAbility.SPECTACLE_ACTIVATION_VALUE_KEY + source.getSourceId());
            if (spectacleActivations != null) {
                return spectacleActivations.contains(game.getState().getZoneChangeCounter(source.getSourceId()) - 1);
            }
            return false;
        } else {
            return source instanceof SpectacleAbility;
        }
    }
}
