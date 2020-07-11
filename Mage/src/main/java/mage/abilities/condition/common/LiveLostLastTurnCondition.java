
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.watchers.WatcherUtils;
import mage.watchers.common.PlayerLostLifeWatcher;

/**
 *
 * @author LevelX
 */
public enum LiveLostLastTurnCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        PlayerLostLifeWatcher watcher = game.getState().getWatcher(PlayerLostLifeWatcher.class);
        if (watcher != null) {
            return watcher.getLifeLostLastTurn(source.getControllerId()) > 0;
        } else {
            WatcherUtils.logMissingWatcher(game, source, PlayerLostLifeWatcher.class, this.getClass());
        }
        return false;
    }
}
