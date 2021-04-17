package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public class DoubleCountersSourceEffect extends OneShotEffect {

    private final CounterType counterType;

    public DoubleCountersSourceEffect(CounterType counterType) {
        super(Outcome.Benefit);
        staticText = "double the number of " + counterType.getName() + " counters on {this}";
        this.counterType = counterType;
    }

    private DoubleCountersSourceEffect(final DoubleCountersSourceEffect effect) {
        super(effect);
        this.counterType = effect.counterType;
    }

    @Override
    public DoubleCountersSourceEffect copy() {
        return new DoubleCountersSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        return permanent.addCounters(counterType.createInstance(
                permanent.getCounters(game).getCount(counterType)
        ), source.getControllerId(), source, game);
    }
}
