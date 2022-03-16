package mage.filter.predicate.permanent;

import mage.counters.Counter;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public enum CounterAnyPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return input
                .getCounters(game)
                .values()
                .stream()
                .mapToInt(Counter::getCount)
                .anyMatch(x -> x > 0);
    }

    @Override
    public String toString() {
        return "any counter";
    }
}
