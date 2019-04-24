package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.watchers.common.LandfallWatcher;

/**
 * @author Loki
 */
public enum LandfallCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        LandfallWatcher watcher = (LandfallWatcher) game.getState().getWatchers().get(LandfallWatcher.class.getSimpleName());
        return watcher != null && watcher.landPlayed(source.getControllerId());
    }
}
