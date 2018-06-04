
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.watchers.Watcher;
import mage.watchers.common.MorbidWatcher;

/**
 * @author nantuko
 */
public enum MorbidCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Watcher watcher = game.getState().getWatchers().get(MorbidWatcher.class.getSimpleName());
        return watcher.conditionMet();
    }

    @Override
    public String toString() {
        return "if a creature died this turn";
    }

}
