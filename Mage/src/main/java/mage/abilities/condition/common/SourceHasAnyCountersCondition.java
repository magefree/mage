package mage.abilities.condition.common;

import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import sun.font.CreatedFontTracker;

import java.util.Objects;

/**
 * @author jeffwadsworth
 */
public class SourceHasAnyCountersCondition implements Predicate<Permanent> {

    final int count;

    public SourceHasAnyCountersCondition(int count) {
        this.count = count;
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.getCounters(game).values().stream().anyMatch(counter -> counter.getCount() >= count);
    }

    @Override
    public String toString() {
        return "any counter";
    }

    @Override
    public int hashCode() {
        return Objects.hash(count);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        SourceHasAnyCountersCondition that = (SourceHasAnyCountersCondition) obj;

        return this.count == that.count;
    }
}
