
package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public class MonocoloredPredicate implements Predicate<MageObject> {

    @Override
    public boolean apply(MageObject input, Game game) {
        return 1 == input.getColor(game).getColorCount();
    }

    @Override
    public String toString() {
        return "Monocolored";
    }
}
