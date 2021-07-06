package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.AbilityType;
import mage.game.Game;
import mage.watchers.common.ManaPaidSourceWatcher;

/**
 * @author TheElk801
 */
public enum TreasureSpentToCastCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getAbilityType() == AbilityType.SPELL) {
            return ManaPaidSourceWatcher.getTreasurePaid(source.getId(), game) > 0;
        }
        return ManaPaidSourceWatcher.getTreasurePaid(source.getSourceId(), game) > 0;
    }
}
