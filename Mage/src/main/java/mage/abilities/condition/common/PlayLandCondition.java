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
        if (game.getTurn().getPhase() == null) { // only for getFrameColor for River of Tears before game started
            return false;
        }
        PlayLandWatcher watcher = game.getState().getWatcher(PlayLandWatcher.class);
        return watcher != null
                && watcher.landPlayed(source.getControllerId());
    }
}
