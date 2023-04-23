package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.ComparisonType;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * Condition which counts the number of a particular kind of counter on
 * permanents, and sees if it exceeds or falls short of a threshold
 * 
 * @author alexander-novo
 */

public class CountersOnPermanentsCondition implements Condition {
    // Which permanents to consider counters on
    public final FilterPermanent filter;
    // Which counter type to count
    public final CounterType counterType;
    // Whether to check if the number of counters exceeds or falls short of the
    // threshold
    public final ComparisonType comparisonType;
    // The threshold to compare against
    public final int threshold;

    /**
     * 
     * @param filter         Which permanents to consider counters on
     * @param counterType    Which counter type to count
     * @param comparisonType Whether to check if the number of counters exceeds or
     *                       falls short of the threshold
     * @param threshold      The threshold to compare against
     */
    public CountersOnPermanentsCondition(FilterPermanent filter, CounterType counterType, ComparisonType comparisonType,
            int threshold) {
        this.filter = filter;
        this.counterType = counterType;
        this.comparisonType = comparisonType;
        this.threshold = threshold;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int totalCounters = 0;
        for (Permanent permanent : game.getBattlefield().getActivePermanents(this.filter,
                source.getControllerId(), source, game)) {
            for (Counter counter : permanent.getCounters(game).values()) {
                if (counter.getName().equals(this.counterType.getName())) {
                    totalCounters += counter.getCount();
                    if (ComparisonType.compare(totalCounters, this.comparisonType, this.threshold)) {
                        return true;
                    }
                }

            }
        }
        return false;
    }

    @Override
    public String toString() {
        String comparisonText;
        switch (this.comparisonType) {
            case MORE_THAN:
                comparisonText = String.format("%d or more", threshold + 1);
                break;
            case EQUAL_TO:
                comparisonText = String.format("%d or fewer", threshold - 1);
                break;
            case FEWER_THAN:
                comparisonText = String.format("%d", threshold);
                break;
            default:
                throw new IllegalArgumentException("comparison rules for " + this.comparisonType + " missing");

        }
        return "there are " + comparisonText + this.counterType.getName() + " counters among "
                + this.filter.getMessage();
    }
}
