package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.counters.CounterType;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;

import java.util.Optional;

/**
 * @author xenohedron
 */
public class ManaValueEqualToCountersSourceCountPredicate implements ObjectSourcePlayerPredicate<MageObject> {

    private final CounterType counterType;

    public ManaValueEqualToCountersSourceCountPredicate(CounterType counterType) {
        this.counterType = counterType;
    }

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        return Optional
                .ofNullable(input.getSource().getSourcePermanentOrLKI(game))
                .map(permanent -> permanent.getCounters(game))
                .map(counters -> counters.getCount(counterType))
                .orElse(-1) // always false
                .equals(input.getObject().getManaValue());
    }

    @Override
    public String toString() {
        return "mana value equal to the number of " + counterType.getName() + " counters on {this}";
    }
}
