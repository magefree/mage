package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author PurpleCrowbar
 */
public class DoubleCountersTargetEffect extends OneShotEffect {

    private final CounterType counterType;

    public DoubleCountersTargetEffect() {
        this((CounterType) null);
    }

    public DoubleCountersTargetEffect(CounterType counterType) {
        super(Outcome.Benefit);
        this.counterType = counterType;
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
        if (counterType != null) {
            return permanent.addCounters(counterType.createInstance(
                    permanent.getCounters(game).getCount(counterType)
            ), source.getControllerId(), source, game);
        }
        for (Counter counter : permanent.getCounters(game).copy().values()) {
            permanent.addCounters(counter, source, game);
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder("double the number of ");
        if (counterType == null) {
            sb.append("each kind of counter on ");
        } else {
            sb.append(counterType);
            sb.append(" counters on ");
        }
        sb.append(getTargetPointer().describeTargets(mode.getTargets(), "it"));
        return sb.toString();
    }
}
