
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.watchers.common.RevoltWatcher;

/**
 * @author emerald000
 */
public enum RevoltCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        RevoltWatcher watcher = game.getState().getWatcher(RevoltWatcher.class);
        return watcher != null && watcher.revoltActive(source.getControllerId());
    }

    @Override
    public String toString() {
        return "a permanent you controlled left the battlefield this turn";
    }
}
