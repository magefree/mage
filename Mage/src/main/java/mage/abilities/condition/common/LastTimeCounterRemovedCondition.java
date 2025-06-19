package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.counters.CounterType;
import mage.game.Game;

import java.util.Optional;

/**
 * Created by glerman on 20/6/15.
 */
public enum LastTimeCounterRemovedCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(source.getSourcePermanentOrLKI(game))
                .map(permanent -> permanent.getCounters(game).getCount(CounterType.TIME))
                .filter(x -> x == 0)
                .isPresent();
    }

    @Override
    public String toString() {
        return "it had no time counters on it";
    }
}
