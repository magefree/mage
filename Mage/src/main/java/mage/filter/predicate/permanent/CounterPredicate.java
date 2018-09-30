package mage.filter.predicate.permanent;

import mage.cards.Card;
import mage.counters.CounterType;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 *
 * @author jeffwadsworth
 */
public class CounterPredicate implements Predicate<Card> {

    private final CounterType counter;

    /**
     *
     * @param counter if null any counter selects the permanent
     */
    public CounterPredicate(CounterType counter) {
        this.counter = counter;
    }

    @Override
    public boolean apply(Card input, Game game) {
        if (counter == null) {
            return !input.getCounters(game).keySet().isEmpty();
        } else {
            return input.getCounters(game).containsKey(counter);
        }
    }

    @Override
    public String toString() {
        return "CounterType(" + counter.getName() + ')';
    }
}
