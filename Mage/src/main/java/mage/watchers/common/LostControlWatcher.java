package mage.watchers.common;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public class LostControlWatcher extends Watcher {

    private final Map<UUID, Long> lastLostControl = new HashMap<>();

    public LostControlWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.LOST_CONTROL) {
            lastLostControl.put(event.getTargetId(), System.currentTimeMillis());
        }
    }

    @Override
    public void reset() {
        super.reset();
        lastLostControl.clear();
    }

    public long getOrderOfLastLostControl(UUID sourceId) {
        return lastLostControl.getOrDefault(sourceId, new Long(0));
    }
}
