
package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.MageObjectReference;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 *
 * @author TheElk801
 */
public class MageObjectReferencePredicate implements Predicate<MageObject> {

    private final MageObjectReference mor;

    public MageObjectReferencePredicate(MageObjectReference mor) {
        this.mor = mor;
    }

    @Override
    public boolean apply(MageObject input, Game game) {
        return mor.refersTo(input, game);
    }

    @Override
    public String toString() {
        return "MageObjectReference(" + mor.toString() + ')';
    }
}
