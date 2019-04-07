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

    public int getZuberasDiedThisTurn() {
        return zuberasDiedThisTurn;
    }

    private int zuberasDiedThisTurn = 0;

    public ZuberasDiedWatcher() {
        super(WatcherScope.GAME);
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
