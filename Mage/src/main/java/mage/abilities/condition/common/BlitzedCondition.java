package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.BlitzAbility;
import mage.game.Game;

import java.util.List;

/**
 * @author TheElk801
 */
public enum BlitzedCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        List<Integer> blitzActivations = (List<Integer>) game.getState().getValue(BlitzAbility.BLITZ_ACTIVATION_VALUE_KEY + source.getSourceId());
        return blitzActivations != null && blitzActivations.contains(game.getState().getZoneChangeCounter(source.getSourceId()));
    }
}
