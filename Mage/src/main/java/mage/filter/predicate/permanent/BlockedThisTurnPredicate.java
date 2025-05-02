package mage.filter.predicate.permanent;

import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.BlockedThisTurnWatcher;

/**
 * Requires BlockedThisTurnWatcher to be added to the card
 *
 * @author xenohedron
 */
public enum BlockedThisTurnPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        BlockedThisTurnWatcher watcher = game.getState().getWatcher(BlockedThisTurnWatcher.class);
        return watcher != null && watcher.checkIfBlocked(input, game);
    }

    @Override
    public String toString() {
        return "blocked this turn";
    }
}
