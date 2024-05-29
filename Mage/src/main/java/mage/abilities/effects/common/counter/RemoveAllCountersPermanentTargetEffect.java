
package mage.abilities.effects.common.counter;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * Removes all counters (optionally of a given counter type) from the target permanent.
 *
 * @author MTGfan
 */
public class RemoveAllCountersPermanentTargetEffect extends OneShotEffect {

    private final CounterType counterType; // If not null, remove counters of that type only.

    public RemoveAllCountersPermanentTargetEffect() {
        this((CounterType) null);
    }

    public RemoveAllCountersPermanentTargetEffect(CounterType counterType) {
        super(Outcome.Neutral);
        this.counterType = counterType;
        staticText = "remove all " + (counterType == null ? "" : counterType.getName() + " ") + "counters from it.";
    }

    public RemoveAllCountersPermanentTargetEffect(RemoveAllCountersPermanentTargetEffect effect) {
        super(effect);
        this.counterType = effect.counterType;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            if (counterType == null) {
                permanent.removeAllCounters(source, game);
            } else {
                permanent.removeAllCounters(counterType.getName(), source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public RemoveAllCountersPermanentTargetEffect copy() {
        return new RemoveAllCountersPermanentTargetEffect(this);
    }
}
