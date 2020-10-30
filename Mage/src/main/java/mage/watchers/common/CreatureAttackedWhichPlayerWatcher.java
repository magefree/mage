package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author jeffwadsworth
 * <p>
 * Return the last player that was attacked by specified creature this turn
 */
public class CreatureAttackedWhichPlayerWatcher extends Watcher {

    private final Map<UUID, UUID> getPlayerAttackedThisTurnByCreature = new HashMap<>();

    public CreatureAttackedWhichPlayerWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED) {
            UUID creatureId = event.getSourceId();
            UUID playerId = event.getTargetId();
            if (playerId != null
                    && creatureId != null) {
                getPlayerAttackedThisTurnByCreature.putIfAbsent(creatureId, playerId);
            }
        }
    }

    public UUID getPlayerAttackedThisTurnByCreature(UUID creatureId) {
        return getPlayerAttackedThisTurnByCreature.getOrDefault(creatureId, null);
    }

    @Override
    public void reset() {
        super.reset();
        getPlayerAttackedThisTurnByCreature.clear();
    }
}
