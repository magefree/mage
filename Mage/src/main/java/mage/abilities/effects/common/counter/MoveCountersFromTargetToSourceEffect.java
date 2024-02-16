package mage.abilities.effects.common.counter;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 * @author xenohedron
 */
public class MoveCountersFromTargetToSourceEffect extends OneShotEffect {

    private final CounterType counterType;
    private final int amount;

    /**
     * Move a +1/+1 counter from target onto {this}
     */
    public MoveCountersFromTargetToSourceEffect() {
        this(CounterType.P1P1);
    }

    /**
     * Move a counter of the specified type from target onto {this}
     */
    public MoveCountersFromTargetToSourceEffect(CounterType counterType) {
        this(counterType, 1);
    }

    /**
     * Move a specific amount of counters of the specified type from target onto {this}
     */
    public MoveCountersFromTargetToSourceEffect(CounterType counterType, int amount) {
        super(Outcome.Neutral);
        this.counterType = counterType;
        this.amount = amount;
    }

    protected MoveCountersFromTargetToSourceEffect(final MoveCountersFromTargetToSourceEffect effect) {
        super(effect);
        this.counterType = effect.counterType;
        this.amount = effect.amount;
    }

    @Override
    public MoveCountersFromTargetToSourceEffect copy() {
        return new MoveCountersFromTargetToSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        Permanent targetPermanent = game.getPermanent(source.getFirstTarget());
        if (sourcePermanent == null || targetPermanent == null
                || sourcePermanent.getId().equals(targetPermanent.getId())
                || targetPermanent.getCounters(game).getCount(counterType) < amount
                || !sourcePermanent.addCounters(counterType.createInstance(amount), source.getControllerId(), source, game)) {
            return false;
        }
        targetPermanent.removeCounters(counterType.createInstance(amount), source, game);
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "move " + CardUtil.numberToText(amount, "a") + ' ' + counterType.getName()
                + (amount > 1 ? " counters" : " counter") + " from "
                + getTargetPointer().describeTargets(mode.getTargets(), "that creature")
                + " onto {this}";
    }
}
