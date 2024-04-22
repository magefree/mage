package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.watchers.common.CreaturesDiedWatcher;

/**
 * @author nantuko
 */
public enum MorbidCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        CreaturesDiedWatcher watcher = game.getState().getWatcher(CreaturesDiedWatcher.class);
        return watcher != null && watcher.conditionMet();
    }

    @Override
    public String toString() {
        return "a creature died this turn";
    }
}
