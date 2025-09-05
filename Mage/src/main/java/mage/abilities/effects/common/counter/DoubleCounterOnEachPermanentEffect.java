package mage.abilities.effects.common.counter;


import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Susucr
 */
public class DoubleCounterOnEachPermanentEffect extends OneShotEffect {

    private final CounterType counterType;
    private final FilterPermanent filter;

    public DoubleCounterOnEachPermanentEffect(CounterType counterType, FilterPermanent filter) {
        super(Outcome.BoostCreature);
        this.counterType = counterType;
        this.filter = filter.copy();
        this.staticText = "double the number of " +
                (counterType != null ? (counterType.getName() + " counters") : "each kind of counter") +
                " on each " + filter.getMessage();
    }

    private DoubleCounterOnEachPermanentEffect(final DoubleCounterOnEachPermanentEffect effect) {
        super(effect);
        this.counterType = effect.counterType;
        this.filter = effect.filter.copy();
    }

    @Override
    public DoubleCounterOnEachPermanentEffect copy() {
        return new DoubleCounterOnEachPermanentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            if (counterType == null) {
                List<Counter> counters = permanent
                        .getCounters(game)
                        .values()
                        .stream()
                        .map(Counter::copy)
                        .collect(Collectors.toList());
                for (Counter counter : counters) {
                    permanent.addCounters(counter, source, game);
                }
                continue;
            }
            int existingCounters = permanent.getCounters(game).getCount(counterType);
            if (existingCounters > 0) {
                permanent.addCounters(counterType.createInstance(existingCounters), source.getControllerId(), source, game);
            }
        }
        return true;
    }
}
