package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public class PutSavedPermanentCountersTargetEffect extends OneShotEffect {

    private final String key;

    public PutSavedPermanentCountersTargetEffect(String key) {
        super(Outcome.Benefit);
        this.key = key;
    }

    private PutSavedPermanentCountersTargetEffect(final PutSavedPermanentCountersTargetEffect effect) {
        super(effect);
        this.key = effect.key;
    }

    @Override
    public PutSavedPermanentCountersTargetEffect copy() {
        return new PutSavedPermanentCountersTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) getValue(key);
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null || creature == null) {
            return false;
        }
        for (Counter counter : permanent.getCounters(game).copy().values()) {
            creature.addCounters(counter, source, game);
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "if it had counters on it, put those counters on " +
                getTargetPointer().describeTargets(mode.getTargets(), "that creature");
    }
}
