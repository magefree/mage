
package mage.filter.predicate.permanent;

import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Saga
 */
public class TransformedPredicate implements Predicate<Permanent> {

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.isTransformed();
    }

    @Override
    public String toString() {
        return "Transformed" ;
    }
}