
package mage.filter.predicate.permanent;

import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author MTGfan
 */
public class ControlledFromStartOfControllerTurnPredicate implements Predicate<Permanent> {

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.wasControlledFromStartOfControllerTurn();
    }

    @Override
    public String toString() {
        return "has controlled since the beginning of the turn";
    }
}
