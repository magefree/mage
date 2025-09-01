package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.watchers.common.PlayerLostLifeWatcher;

/**
 * @author LevelX
 */
public enum LiveLostLastTurnCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        PlayerLostLifeWatcher watcher = game.getState().getWatcher(PlayerLostLifeWatcher.class);
        return watcher != null && watcher.getLifeLostLastTurn(source.getControllerId()) > 0;
    }

    @Override
    public String toString() {
        return "you lost life last turn";
    }
}
