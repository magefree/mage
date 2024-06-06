package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.game.Game;

/**
 * @author xenohedron
 */
public class RemoveAllCountersAllEffect extends OneShotEffect {

    private final CounterType counterType;
    private final FilterPermanent filter;

    public RemoveAllCountersAllEffect(CounterType counterType, FilterPermanent filter) {
        super(Outcome.Neutral);
        this.counterType = counterType;
        this.filter = filter;
        staticText = "remove all " + counterType.getName() + " counters from all " + filter.getMessage();
    }

    protected RemoveAllCountersAllEffect(final RemoveAllCountersAllEffect effect) {
        super(effect);
        this.counterType = effect.counterType;
        this.filter = effect.filter;
    }

    @Override
    public RemoveAllCountersAllEffect copy() {
        return new RemoveAllCountersAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)
                .forEach(p -> p.removeAllCounters(counterType.getName(), source, game));
        return true;
    }
}
