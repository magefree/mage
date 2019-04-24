
package mage.watchers.common;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class RevoltWatcher extends Watcher {

    private final Set<UUID> revoltActivePlayerIds = new HashSet<>(0);

    public RevoltWatcher() {
        super(RevoltWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public RevoltWatcher(final RevoltWatcher watcher) {
        super(watcher);
        this.revoltActivePlayerIds.addAll(watcher.revoltActivePlayerIds);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == EventType.ZONE_CHANGE && event instanceof ZoneChangeEvent) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD) {
                Permanent permanent = zEvent.getTarget();
                if (permanent != null) {
                    revoltActivePlayerIds.add(permanent.getControllerId());
                }
            }
        }
    }

    public boolean revoltActive(UUID playerId) {
        return revoltActivePlayerIds.contains(playerId);
    }

    @Override
    public void reset() {
        revoltActivePlayerIds.clear();
    }

    @Override
    public RevoltWatcher copy() {
        return new RevoltWatcher(this);
    }
}
