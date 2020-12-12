
package mage.abilities.effects.common.counter;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author MTGfan
 */
public class RemoveAllCountersTargetEffect extends OneShotEffect {
    
    private final CounterType counterType;

    public RemoveAllCountersTargetEffect(CounterType counterType) {
        super(Outcome.Neutral);
        this.counterType = counterType;
        staticText = "remove all " + counterType.getName() + " counters from it.";
    }

    public RemoveAllCountersTargetEffect(RemoveAllCountersTargetEffect effect) {
        super(effect);
        this.counterType = effect.counterType;
    }

    @Override
    public boolean apply(Game game, Ability source) {
      Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
      if(permanent != null) {
          int count = permanent.getCounters(game).getCount(counterType);
          permanent.removeCounters(counterType.getName(), count, source, game);
          return true;
      }
      return false;
    }

    @Override
    public RemoveAllCountersTargetEffect copy() {
        return new RemoveAllCountersTargetEffect(this);
    }
}
