
package mage.filter.predicate.permanent;

import mage.MageObject;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;

/**
 * @author North
 */
public enum AnotherPredicate implements ObjectSourcePlayerPredicate<ObjectSourcePlayer<MageObject>> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        return !input.getObject().getId().equals(input.getSourceId());
    }

    @Override
    public String toString() {
        return "Another";
    }
}
