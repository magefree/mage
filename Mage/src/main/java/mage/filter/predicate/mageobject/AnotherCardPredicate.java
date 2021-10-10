
package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;

/**
 *
 * @author North
 */
public class AnotherCardPredicate implements ObjectSourcePlayerPredicate<MageObject> {

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        return !input.getSourceId().equals(input.getObject().getId());
    }

    @Override
    public String toString() {
        return "Another card";
    }
}