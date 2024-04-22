
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LoneFox
 */
public class RemoveAllCountersSourceEffect extends OneShotEffect {

    private final CounterType counterType;

    public RemoveAllCountersSourceEffect(CounterType counterType) {
        super(Outcome.Neutral);
        this.counterType = counterType;
        staticText = "remove all " + counterType.getName() + " counters from it.";
    }

    protected RemoveAllCountersSourceEffect(final RemoveAllCountersSourceEffect effect) {
        super(effect);
        this.counterType = effect.counterType;
    }

    @Override
    public RemoveAllCountersSourceEffect copy() {
        return new RemoveAllCountersSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            int count = sourcePermanent.getCounters(game).getCount(counterType);
            sourcePermanent.removeCounters(counterType.getName(), count, source, game);
            return true;
        }
        return false;
    }
}
