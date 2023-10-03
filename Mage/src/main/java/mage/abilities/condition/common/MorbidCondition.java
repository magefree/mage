package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.watchers.common.MorbidWatcher;

/**
 * @author nantuko
 */
public enum MorbidCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        MorbidWatcher watcher = game.getState().getWatcher(MorbidWatcher.class);
        return watcher != null && watcher.conditionMet();
    }

    @Override
    public String toString() {
        return "a creature died this turn";
    }
}
