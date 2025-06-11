package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 * @author werhsdnas
 */
public enum PowerParityPredicate implements Predicate<MageObject> {
    EVEN(0),
    ODD(1);
    private final int parity;

    PowerParityPredicate(int parity) {
        this.parity = parity;
    }

    @Override
    public boolean apply(MageObject input, Game game) {
        return input.getPower().getValue() % 2 == parity;
    }

    @Override
    public String toString() {
        return "PowerParity" + super.toString();
    }
}