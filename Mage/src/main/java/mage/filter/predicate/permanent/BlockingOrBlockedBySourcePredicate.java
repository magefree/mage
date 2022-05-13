package mage.filter.predicate.permanent;

import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.BlockingOrBlockedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public enum BlockingOrBlockedBySourcePredicate implements ObjectSourcePlayerPredicate<Permanent> {
    BLOCKING,
    BLOCKED_BY,
    EITHER;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        Permanent thisCreature = input.getSource().getSourcePermanentOrLKI(game);
        Permanent otherCreature = input.getObject();
        switch (this) {
            case BLOCKING:
                return BlockingOrBlockedWatcher.check(thisCreature, otherCreature, game);
            case BLOCKED_BY:
                return BlockingOrBlockedWatcher.check(otherCreature, thisCreature, game);
            case EITHER:
                return BlockingOrBlockedWatcher.check(otherCreature, thisCreature, game)
                        || BlockingOrBlockedWatcher.check(thisCreature, otherCreature, game);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Blocking or blocked by";
    }
}
