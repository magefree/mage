
package mage.filter.predicate.permanent;

import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author North
 */
public class BlockingPredicate implements Predicate<Permanent> {

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.getBlocking() > 0;
    }

    @Override
    public String toString() {
        return "Blocking";
    }
}
