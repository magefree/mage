package mage.filter.predicate.mageobject;

import mage.MageInt;
import mage.MageObject;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 * @author xenohedron
 */
public enum PowerGreaterThanBasePowerPredicate implements Predicate<MageObject> {
    instance;

    @Override
    public boolean apply(MageObject input, Game game) {
        MageInt power = input.getPower();
        return power.getValue() > power.getModifiedBaseValue();
    }

    @Override
    public String toString() {
        return "power greater than its base power";
    }
}
