package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 * @author TheElk801
 * TODO: make this actually work
 */
public enum AdventurePredicate implements Predicate<MageObject> {
    instance;

    @Override
    public boolean apply(MageObject input, Game game) {
        return false;
    }

    @Override
    public String toString() {
        return "Adventure";
    }
}
