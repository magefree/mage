
package mage.filter.predicate.permanent;

import mage.counters.Counter;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class CounterAnyPredicate implements Predicate<Permanent> {

    public CounterAnyPredicate() {
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.getCounters(game).values().stream().anyMatch(counter -> counter.getCount() > 0);

    }

    @Override
    public String toString() {
        return "any counter";
    }
}
