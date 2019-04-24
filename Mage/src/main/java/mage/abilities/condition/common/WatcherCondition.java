package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.watchers.Watcher;

/**
 *
 * @author Quercitron
 */
public class WatcherCondition implements Condition {

    private final String watcherKey;
    private final WatcherScope watcherScope;
    private final String text;

    public WatcherCondition(String watcherKey, WatcherScope watcherScope) {
        this(watcherKey, watcherScope, "");
    }

    public WatcherCondition(String watcherKey, WatcherScope watcherScope, String text) {
        this.watcherKey = watcherKey;
        this.watcherScope = watcherScope;
        this.text = text;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Watcher watcher = null;
        switch (watcherScope) {
            case GAME:
                watcher = game.getState().getWatchers().get(watcherKey);
                break;
            case PLAYER:
                watcher = game.getState().getWatchers().get(watcherKey, source.getControllerId());
                break;
            case CARD:
                watcher = game.getState().getWatchers().get(watcherKey, source.getSourceId());
                break;
        }
        return watcher != null && watcher.conditionMet();
    }

    @Override
    public String toString() {
        return text;
    }

}
