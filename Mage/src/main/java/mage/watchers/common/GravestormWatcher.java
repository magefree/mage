
package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.constants.Zone;
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
        super(GravestormWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public GravestormWatcher(final GravestormWatcher watcher) {
        super(watcher);
        this.gravestormCount = watcher.gravestormCount;
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD && zEvent.getToZone() == Zone.GRAVEYARD) {
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

    @Override
    public GravestormWatcher copy() {
        return new GravestormWatcher(this);
    }
}
