package mage.watchers.common;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.util.CardUtil;
import mage.watchers.Watcher;

/**
 * Watcher stores which sources did damage to a player
 * 
 * @author LevelX
 */
public class PlayerDamagedBySourceWatcher extends Watcher {

    private final Set<String> damageSourceIds = new HashSet<>();
    private final Set<String> combatDamageSourceIds = new HashSet<>();

    public PlayerDamagedBySourceWatcher() {
        super(WatcherScope.PLAYER);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER) {
            if (event.getTargetId().equals(controllerId)) {
                String sourceId = CardUtil.getCardZoneString(null, event.getSourceId(), game);
                damageSourceIds.add(sourceId);
                if (((DamagedEvent) event).isCombatDamage()) {
                    combatDamageSourceIds.add(sourceId);
                }
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

    public boolean hasSourceDoneCombatDamage(UUID sourceId, Game game) {
        return combatDamageSourceIds.contains(CardUtil.getCardZoneString(null, sourceId, game));
    }

    @Override
    public void reset() {
        super.reset();
        damageSourceIds.clear();
        combatDamageSourceIds.clear();
    }
}
