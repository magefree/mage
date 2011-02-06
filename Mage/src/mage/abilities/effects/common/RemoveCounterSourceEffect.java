package mage.abilities.effects.common;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;

public class RemoveCounterSourceEffect extends OneShotEffect<RemoveCounterSourceEffect> {
    private Counter counter;

    public RemoveCounterSourceEffect(Counter counter) {
        super(Constants.Outcome.UnboostCreature);
        this.counter = counter;
    }

    public RemoveCounterSourceEffect(RemoveCounterSourceEffect effect) {
        super(effect);
        this.counter = effect.counter.copy();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent p = game.getPermanent(source.getSourceId());
        if (p != null && p.getCounters().getCount(counter.getName()) >= counter.getCount()) {
            p.removeCounters(counter.getName(), counter.getCount(), game);
            return true;
        }
        return false;
    }

    @Override
    public RemoveCounterSourceEffect copy() {
        return new RemoveCounterSourceEffect(this);
    }
}
