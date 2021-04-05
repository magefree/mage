package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.IntCompareCondition;
import mage.constants.ComparisonType;
import mage.game.Game;
import mage.watchers.common.PlayerGainedLifeWatcher;

/**
 * Created by IGOUDT on 5-4-2017.
 */
public class YouGainedLifeCondition extends IntCompareCondition {

    public YouGainedLifeCondition(ComparisonType type, int value) {
        super(type, value);
    }

    @Override
    protected int getInputValue(Game game, Ability source) {
        PlayerGainedLifeWatcher watcher = game.getState().getWatcher(PlayerGainedLifeWatcher.class);
        return watcher == null ? 0 : watcher.getLifeGained(source.getControllerId());
    }

    @Override
    public String toString() {
        return "if you gained " + (value == 0 ? "" : (value + 1) + " or more ") + "life this turn";
    }
}
