
package mage.filter.predicate.permanent;

import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author TheElk801
 */
public class DefendingPlayerControlsPredicate implements ObjectSourcePlayerPredicate<ObjectSourcePlayer<Permanent>> {

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return input.getObject().isControlledBy(game.getCombat().getDefendingPlayerId(input.getSourceId(), game));
    }

    @Override
    public String toString() {
        return "Another";
    }
}
