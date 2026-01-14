package mage.filter.predicate.permanent;

import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public class CanHaveCounterAddedPredicate implements Predicate<Permanent> {

    private final CounterType counterType;

    public CanHaveCounterAddedPredicate(Counter counter) {
        this(CounterType.findByName(counter.getName()));
    }

    public CanHaveCounterAddedPredicate(CounterType counterType) {
        this.counterType = counterType;
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.canHaveCounterAdded(counterType);
    }
}
