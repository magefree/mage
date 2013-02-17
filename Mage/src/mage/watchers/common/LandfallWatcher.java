package mage.watchers.common;

import mage.Constants;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.WatcherImpl;

/**
 * @author BetaSteward_at_googlemail.com
 * @author Loki
 */
public class LandfallWatcher extends WatcherImpl<LandfallWatcher> {

    public LandfallWatcher() {
        super("LandPlayed", Constants.WatcherScope.PLAYER);
    }

    public LandfallWatcher(final LandfallWatcher watcher) {
        super(watcher);
    }

    @Override
    public LandfallWatcher copy() {
        return new LandfallWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (condition == true) { //no need to check - condition has already occured
            return;
        }
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent.getCardType().contains(Constants.CardType.LAND) && permanent.getControllerId().equals(this.controllerId)) {
                condition = true;
            }
        }
    }

}
