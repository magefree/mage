package mage.filter.predicate.permanent;

import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author North
 */
public enum TappedPredicate implements Predicate<Permanent> {
    TAPPED(true), UNTAPPED(false);

    private final boolean value;

    TappedPredicate(boolean value) {
        this.value = value;
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.isTapped() == value;
    }

    @Override
    public String toString() {
        return "Tapped";
    }
}
