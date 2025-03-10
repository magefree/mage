package mage.filter.predicate.permanent;

import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author balazskristof
 */
public enum AttackedThisTurnPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        AttackedThisTurnWatcher watcher = game.getState().getWatcher(AttackedThisTurnWatcher.class);
        return watcher != null && watcher.checkIfAttacked(input, game);
    }

    @Override
    public String toString() {
        return "attacked this turn";
    }
}
