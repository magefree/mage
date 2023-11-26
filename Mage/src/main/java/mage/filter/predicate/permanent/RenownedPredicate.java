
package mage.filter.predicate.permanent;

import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author notgreat
 */
public enum RenownedPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.isRenowned();
    }

    @Override
    public String toString() {
        return "Renowned";
    }
}
