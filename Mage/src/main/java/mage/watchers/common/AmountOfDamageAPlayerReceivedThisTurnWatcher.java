package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

/**
 * @author jeffwadsworth
 * <p>
 * Amount of damage received by a player this turn
 */
public class AmountOfDamageAPlayerReceivedThisTurnWatcher extends Watcher {

    private final Map<UUID, Integer> amountOfDamageReceivedThisTurn = new HashMap<>();

    public AmountOfDamageAPlayerReceivedThisTurnWatcher() {
        super(WatcherScope.GAME);
    }

    public AmountOfDamageAPlayerReceivedThisTurnWatcher(final AmountOfDamageAPlayerReceivedThisTurnWatcher watcher) {
        super(watcher);
        for (Entry<UUID, Integer> entry : watcher.amountOfDamageReceivedThisTurn.entrySet()) {
            amountOfDamageReceivedThisTurn.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER) {
            UUID playerId = event.getTargetId();
            if (playerId != null) {
                amountOfDamageReceivedThisTurn.putIfAbsent(playerId, 0);
                amountOfDamageReceivedThisTurn.compute(playerId, (k, v) -> v + event.getAmount());
            }
        }
    }

    public int getAmountOfDamageReceivedThisTurn(UUID playerId) {
        return amountOfDamageReceivedThisTurn.getOrDefault(playerId, 0);
    }

    @Override
    public void reset() {
        super.reset();
        amountOfDamageReceivedThisTurn.clear();
    }

    @Override
    public AmountOfDamageAPlayerReceivedThisTurnWatcher copy() {
        return new AmountOfDamageAPlayerReceivedThisTurnWatcher(this);
    }
}
