package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.condition.IntCompareCondition;
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
        int gainedLife = 0;
        PlayerGainedLifeWatcher watcher = (PlayerGainedLifeWatcher) game.getState().getWatchers().get(PlayerGainedLifeWatcher.class.getName());
        if (watcher != null) {
            gainedLife = watcher.getLiveGained(source.getControllerId());
        }
        return gainedLife;
    }

    @Override
    public String toString() {
        return String.format("if you gained %s or more life this turn ", value);
    }
}

