
package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.constants.SubType;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 *
 * @author North
 */
public class SubtypePredicate implements Predicate<MageObject> {

    private final SubType subtype;

    public SubtypePredicate(SubType subtype) {
        this.subtype = subtype;
    }


    @Override
    public boolean apply(MageObject input, Game game) {
        return input.hasSubtype(subtype, game);
    }

    @Override
    public String toString() {
        return "Subtype(" + subtype + ')';
    }
}
