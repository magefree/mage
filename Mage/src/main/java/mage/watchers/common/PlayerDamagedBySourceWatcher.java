
package mage.watchers.common;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.util.CardUtil;
import mage.watchers.Watcher;

/**
 * Watcher stores whitch sources did damage to a player
 * 
 * @author LevelX
 */
public class PlayerDamagedBySourceWatcher extends Watcher {

    private final Set<String> damageSourceIds = new HashSet<>();

    public PlayerDamagedBySourceWatcher() {
        super(WatcherScope.PLAYER);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER) {
            if (event.getTargetId().equals(controllerId)) {
                damageSourceIds.add(CardUtil.getCardZoneString(null, event.getSourceId(), game));
            }
        }
    }

    /**
     * Checks if the current object with sourceId has damaged the player during the current turn.
     * The zoneChangeCounter will be taken into account.
     * 
     * @param sourceId
     * @param game
     * @return 
     */
    public boolean hasSourceDoneDamage(UUID sourceId, Game game) {
        return damageSourceIds.contains(CardUtil.getCardZoneString(null, sourceId, game));
    }

    @Override
    public void reset() {
        super.reset();
        damageSourceIds.clear();
    }
}
