package mage.abilities.condition.common;

import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

// @author jeffwadsworth

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

}
