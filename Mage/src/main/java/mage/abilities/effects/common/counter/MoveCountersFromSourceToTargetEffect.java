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
public class MoveCountersFromSourceToTargetEffect extends OneShotEffect {

    private final CounterType counterType;
    private final int amount;

    /**
     * Move a +1/+1 counter from {this} onto target
     */
    public MoveCountersFromSourceToTargetEffect() {
        this(CounterType.P1P1);
    }

    /**
     * Move a counter of the specified type from {this} onto target
     */
    public MoveCountersFromSourceToTargetEffect(CounterType counterType) {
        this(counterType, 1);
    }

    /**
     * Move a specific amount of counters of the specified type from {this} onto target
     */
    public MoveCountersFromSourceToTargetEffect(CounterType counterType, int amount) {
        super(Outcome.Neutral);
        this.counterType = counterType;
        this.amount = amount;
    }

    protected MoveCountersFromSourceToTargetEffect(final MoveCountersFromSourceToTargetEffect effect) {
        super(effect);
        this.counterType = effect.counterType;
        this.amount = effect.amount;
    }

    @Override
    public MoveCountersFromSourceToTargetEffect copy() {
        return new MoveCountersFromSourceToTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        Permanent targetPermanent = game.getPermanent(source.getFirstTarget());
        if (sourcePermanent == null || targetPermanent == null
                || sourcePermanent.getId().equals(targetPermanent.getId())
                || sourcePermanent.getCounters(game).getCount(counterType) < amount
                || !targetPermanent.addCounters(counterType.createInstance(amount), source.getControllerId(), source, game)) {
            return false;
        }
        sourcePermanent.removeCounters(counterType.createInstance(amount), source, game);
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "move " + CardUtil.numberToText(amount, "a") + ' ' + counterType.getName()
                + (amount > 1 ? " counters" : " counter") + " from {this} onto "
                + getTargetPointer().describeTargets(mode.getTargets(), "that creature");
    }
}
