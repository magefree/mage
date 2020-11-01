package mage.filter.predicate.permanent;

import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public enum EnteredThisTurnPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.getTurnsOnBattlefield() == 0;
    }

    @Override
    public String toString() {
        return "Entered this turn";
    }
}
