
package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.constants.SuperType;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 *
 * @author North
 */
public class SupertypePredicate implements Predicate<MageObject> {

    private final SuperType supertype;

    public SupertypePredicate(SuperType supertype) {
        this.supertype = supertype;
    }

    @Override
    public boolean apply(MageObject input, Game game) {
        return input.getSuperType().contains(supertype);
    }

    @Override
    public String toString() {
        return "Supertype(" + supertype + ')';
    }
}
