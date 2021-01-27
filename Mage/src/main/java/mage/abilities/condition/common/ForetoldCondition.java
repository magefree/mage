package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.watchers.common.ForetoldWatcher;

/**
 *
 * @author jeffwadsworth
 */
public enum ForetoldCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        ForetoldWatcher watcher = game.getState().getWatcher(ForetoldWatcher.class);
        if (watcher != null) {
            return watcher.foretoldSpellWasCast(source.getSourceId());
        }
        return false;
    }

    @Override
    public String toString() {
        return "this card was foretold";
    }
}