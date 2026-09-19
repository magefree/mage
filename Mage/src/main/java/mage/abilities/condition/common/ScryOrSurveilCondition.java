package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.watchers.common.ScryOrSurveilWatcher;

/**
 * @author muz
 */
public enum ScryOrSurveilCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        ScryOrSurveilWatcher watcher = game.getState().getWatcher(ScryOrSurveilWatcher.class);
        return watcher != null && watcher.hasScriedOrSurveilled(source.getControllerId());
    }

    @Override
    public String toString() {
        return "you've scried or surveilled this turn";
    }
}