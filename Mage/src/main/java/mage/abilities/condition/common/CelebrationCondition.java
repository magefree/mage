
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.watchers.common.CelebrationWatcher;

/**
 * @author Susucr
 */
public enum CelebrationCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        CelebrationWatcher watcher = game.getState().getWatcher(CelebrationWatcher.class);
        return watcher != null && watcher.celebrationActive(source.getControllerId());
    }

    @Override
    public String toString() {
        return "two or more nonland permanents entered the battlefield under your control this turn";
    }
}
