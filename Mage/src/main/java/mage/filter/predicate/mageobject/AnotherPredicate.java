package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;

/**
 * @author North
 */
public enum AnotherPredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        if (!input.getObject().getId().equals(input.getSourceId())) {
            return true;
        }
        int zcc = input.getSource().getSourceObjectZoneChangeCounter();
        return zcc != 0 && zcc != input.getObject().getZoneChangeCounter(game);
    }

    @Override
    public String toString() {
        return "Another";
    }
}
