package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public class PutSourceCountersOnTargetEffect extends OneShotEffect {

    private final CounterType counterType;

    public PutSourceCountersOnTargetEffect() {
        this((CounterType) null);
    }

    public PutSourceCountersOnTargetEffect(CounterType counterType) {
        super(Outcome.Benefit);
        this.counterType = counterType;
    }

    private PutSourceCountersOnTargetEffect(final PutSourceCountersOnTargetEffect effect) {
        super(effect);
        this.counterType = effect.counterType;
    }

    @Override
    public PutSourceCountersOnTargetEffect copy() {
        return new PutSourceCountersOnTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = source.getSourcePermanentOrLKI(game);
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (sourcePermanent == null || permanent == null) {
            return false;
        }
        if (counterType != null) {
            int count = sourcePermanent.getCounters(game).getCount(counterType);
            return count > 0 && permanent.addCounters(counterType.createInstance(count), source, game);
        }
        sourcePermanent
                .getCounters(game)
                .values()
                .stream()
                .forEach(counter -> permanent.addCounters(counter, source.getControllerId(), source, game));
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "put its" + (counterType != null ? " " + counterType : "") + " counters on " +
                getTargetPointer().describeTargets(mode.getTargets(), "that creature");
    }
}
