package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author PurpleCrowbar
 */
public class DoubleCountersTargetEffect extends OneShotEffect {

    private final CounterType counterType;

    public DoubleCountersTargetEffect(CounterType counterType) {
        super(Outcome.Benefit);
        this.counterType = counterType;
        staticText = "double the number of " + counterType.getName() + " counters on it";
    }

    private DoubleCountersTargetEffect(final DoubleCountersTargetEffect effect) {
        super(effect);
        this.counterType = effect.counterType;
    }

    @Override
    public DoubleCountersTargetEffect copy() {
        return new DoubleCountersTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        return permanent.addCounters(counterType.createInstance(
                permanent.getCounters(game).getCount(counterType)
        ), source.getControllerId(), source, game);
    }
}
