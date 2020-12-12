
package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.watchers.Watcher;

/**
 *
 * @author emerald000
 *
 */
public class GravestormWatcher extends Watcher {

    private int gravestormCount = 0;

    public GravestormWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.isDiesEvent()) {
                gravestormCount++;
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        this.gravestormCount = 0;
    }

    public int getGravestormCount() {
        return this.gravestormCount;
    }
}
