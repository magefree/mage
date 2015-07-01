package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * Created by glerman on 20/6/15.
 */
public class LastTimeCounterRemovedCondition implements Condition{


  private static final LastTimeCounterRemovedCondition fInstance = new LastTimeCounterRemovedCondition();

  public static LastTimeCounterRemovedCondition getInstance() {
    return fInstance;
  }

  @Override
  public boolean apply(Game game, Ability source) {
    Permanent permanent = game.getPermanent(source.getSourceId());
    if (permanent == null) {
      permanent = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
    }
    if (permanent != null) {
      final int timeCounters = permanent.getCounters().getCount(CounterType.TIME);
      return timeCounters == 0;
    } else {
      return false;
    }
  }
}
