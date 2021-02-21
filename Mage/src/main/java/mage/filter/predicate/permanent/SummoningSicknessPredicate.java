package mage.filter.predicate.permanent;

import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class SummoningSicknessPredicate implements Predicate<Permanent> {

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.hasSummoningSickness();
    }

    @Override
    public String toString() {
        return "Summoning sickness";
    }
}
