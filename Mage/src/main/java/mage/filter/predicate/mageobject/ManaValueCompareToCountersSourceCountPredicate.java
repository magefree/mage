package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.constants.ComparisonType;
import mage.counters.CounterType;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;

import java.util.Optional;

/**
 * @author jmlundeen
 */
public class ManaValueCompareToCountersSourceCountPredicate implements ObjectSourcePlayerPredicate<MageObject> {

    private final CounterType counterType;
    private final ComparisonType comparisonType;

    public ManaValueCompareToCountersSourceCountPredicate(CounterType counterType, ComparisonType comparisonType) {
        this.counterType = counterType;
        this.comparisonType = comparisonType;
    }

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        int counterCount = Optional
                .ofNullable(input.getSource().getSourcePermanentOrLKI(game))
                .map(permanent -> permanent.getCounters(game))
                .map(counters -> counters.getCount(counterType))
                .orElse(-1); // always false
        return ComparisonType.compare(input.getObject().getManaValue(), comparisonType, counterCount);
    }

    @Override
    public String toString() {
        return "mana value " + comparisonType.toString() + " to the number of " + counterType.getName() + " counters on {this}";
    }
}
