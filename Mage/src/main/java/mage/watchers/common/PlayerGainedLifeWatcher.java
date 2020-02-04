
package mage.watchers.common;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

/**
 * Counts amount of life gained during the current turn by players.
 *
 * @author LevelX2
 */
public class PlayerGainedLifeWatcher extends Watcher {

    private final Map<UUID, Integer> amountOfLifeGainedThisTurn = new HashMap<>();

    public PlayerGainedLifeWatcher() {
        super(WatcherScope.GAME);
    }


    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.UNTAP_STEP_PRE) {
            reset();
        }
        if (event.getType() == GameEvent.EventType.GAINED_LIFE) {
            UUID playerId = event.getPlayerId();
            if (playerId != null) {
                amountOfLifeGainedThisTurn.putIfAbsent(playerId, 0);
                amountOfLifeGainedThisTurn.compute(playerId, (p, amount) -> amount + event.getAmount());
            }
        }
    }

    public int getLifeGained(UUID playerId) {
        return amountOfLifeGainedThisTurn.getOrDefault(playerId, 0);
    }

    @Override
    public void reset() {
        super.reset();
        amountOfLifeGainedThisTurn.clear();
    }
}
