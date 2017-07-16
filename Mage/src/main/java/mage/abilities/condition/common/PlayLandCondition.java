package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.watchers.common.PlayLandWatcher;

/**
 * @author jeffwadsworth
 */
public enum PlayLandCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        PlayLandWatcher watcher = (PlayLandWatcher) game.getState().getWatchers().get(PlayLandWatcher.class.getSimpleName());
        return watcher != null
                && watcher.landPlayed(source.getControllerId());
    }
}
