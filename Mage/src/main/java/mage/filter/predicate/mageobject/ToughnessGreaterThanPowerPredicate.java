package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 * @author xenohedron
 */
public enum ToughnessGreaterThanPowerPredicate implements Predicate<MageObject> {
    instance;

    @Override
    public boolean apply(MageObject input, Game game) {
        return input.getToughness().getValue() > input.getPower().getValue();
    }

    @Override
    public String toString() {
        return "toughness greater than its power";
    }
}
