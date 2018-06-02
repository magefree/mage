
package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 *
 * @author North
 */
public class ColorlessPredicate implements Predicate<MageObject> {

    @Override
    public boolean apply(MageObject input, Game game) {
        return input.getColor(game).isColorless();
    }

    @Override
    public String toString() {
        return "Colorless";
    }
}
