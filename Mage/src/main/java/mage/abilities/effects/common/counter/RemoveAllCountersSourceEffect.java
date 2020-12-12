
package mage.abilities.effects.common.counter;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author TheElk801
 */
public class RemoveAllCountersSourceEffect extends OneShotEffect {

    private final CounterType counterType;

    public RemoveAllCountersSourceEffect(CounterType counterType) {
        super(Outcome.Neutral);
        this.counterType = counterType;
        staticText = "remove all " + counterType.getName() + " counters from {this}.";
    }

    public RemoveAllCountersSourceEffect(RemoveAllCountersSourceEffect effect) {
        super(effect);
        this.counterType = effect.counterType;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null) {
            int count = permanent.getCounters(game).getCount(counterType);
            permanent.removeCounters(counterType.getName(), count, source, game);
            return true;
        }
        return false;
    }

    @Override
    public RemoveAllCountersSourceEffect copy() {
        return new RemoveAllCountersSourceEffect(this);
    }
}
