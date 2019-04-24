
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.watchers.common.PlayerAttackedWatcher;

/**
 * @author LevelX2
 */
public enum RaidCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        PlayerAttackedWatcher watcher = (PlayerAttackedWatcher) game.getState().getWatchers().get(PlayerAttackedWatcher.class.getSimpleName());
        return watcher != null && watcher.getNumberOfAttackersCurrentTurn(source.getControllerId()) > 0;
    }

    public String toString() {
        return "if you attacked with a creature this turn";
    }
}
