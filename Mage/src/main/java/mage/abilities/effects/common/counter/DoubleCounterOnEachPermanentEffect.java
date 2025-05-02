package mage.abilities.effects.common.counter;


import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;

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
        this.staticText = "double the number of " + counterType.getName() + " counters on each " + filter.getMessage();
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
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game);
        for (Permanent permanent : permanents) {
            int existingCounters = permanent.getCounters(game).getCount(counterType);
            if (existingCounters > 0) {
                permanent.addCounters(counterType.createInstance(existingCounters), source.getControllerId(), source, game);
            }
        }
        return true;
    }
}
