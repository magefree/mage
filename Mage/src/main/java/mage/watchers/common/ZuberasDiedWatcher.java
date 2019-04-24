package mage.watchers.common;

import mage.MageObject;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.watchers.Watcher;

/**
 * Created by Eric on 9/24/2016.
 */
public class ZuberasDiedWatcher extends Watcher {

    public int zuberasDiedThisTurn = 0;

    public ZuberasDiedWatcher() {
        super(ZuberasDiedWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public ZuberasDiedWatcher(final ZuberasDiedWatcher watcher) {
        super(watcher);
        this.zuberasDiedThisTurn = watcher.zuberasDiedThisTurn;
    }

    @Override
    public ZuberasDiedWatcher copy() {
        return new ZuberasDiedWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent) event).isDiesEvent()) {
            MageObject card = game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (card != null && card.hasSubtype(SubType.ZUBERA, game)) {
                zuberasDiedThisTurn++;
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        zuberasDiedThisTurn = 0;
    }

}
