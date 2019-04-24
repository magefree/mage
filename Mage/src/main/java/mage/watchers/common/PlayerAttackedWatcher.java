

package mage.watchers.common;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

/**
 * @author LevelX2
 */

public class PlayerAttackedWatcher extends Watcher {

    // With how many creatures attacked this player this turn
    private final Map<UUID, Integer> playerAttacked = new HashMap<>();

    public PlayerAttackedWatcher() {
        super(PlayerAttackedWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public PlayerAttackedWatcher(final PlayerAttackedWatcher watcher) {
        super(watcher);
        for (Map.Entry<UUID, Integer> entry : watcher.playerAttacked.entrySet()) {
            this.playerAttacked.put(entry.getKey(), entry.getValue());

        }
    }

    @Override
    public PlayerAttackedWatcher copy() {
        return new PlayerAttackedWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED) {
            playerAttacked.putIfAbsent(event.getPlayerId(), 0);
            playerAttacked.compute(event.getPlayerId(), (p, amount) -> amount + 1);
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerAttacked.clear();
    }

    public int getNumberOfAttackersCurrentTurn(UUID playerId) {
        return playerAttacked.getOrDefault(playerId, 0);
    }
}
