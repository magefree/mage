package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class RevoltWatcher extends Watcher {

    private final Set<UUID> revoltActivePlayerIds = new HashSet<>(0);

    public RevoltWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && event instanceof ZoneChangeEvent) {
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

    public static boolean checkAny(Game game) {
        return !game
                .getState()
                .getWatcher(RevoltWatcher.class)
                .revoltActivePlayerIds
                .isEmpty();
    }

    @Override
    public void reset() {
        super.reset();
        revoltActivePlayerIds.clear();
    }
}
