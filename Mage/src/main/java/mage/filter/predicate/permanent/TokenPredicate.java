package mage.filter.predicate.permanent;

import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;

/**
 * @author North
 */
public enum TokenPredicate implements Predicate<Permanent> {
    TRUE(true),
    FALSE(false);
    private final boolean value;

    TokenPredicate(boolean value) {
        this.value = value;
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        return value == input instanceof PermanentToken;
    }

    @Override
    public String toString() {
        return "Token";
    }
}
