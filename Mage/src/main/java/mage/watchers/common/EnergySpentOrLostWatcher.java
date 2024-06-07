package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EnergySpentOrLostWatcher extends Watcher {

    // player -> amount of energy spent or lost this turn
    private final Map<UUID, Integer> energyLostOrSpent = new HashMap<>();

    public EnergySpentOrLostWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.COUNTERS_REMOVED) {
            return;
        }
        if (!event.getData().equals(CounterType.ENERGY.getName())) {
            return;
        }
        int amount = event.getAmount();
        if (amount <= 0) {
            return;
        }
        energyLostOrSpent.compute(event.getTargetId(), (k, i) -> i == null ? amount : Integer.sum(i, amount));
    }

    @Override
    public void reset() {
        super.reset();
        energyLostOrSpent.clear();
    }

    public static int getAmountEnergyLostOrSpentThisTurn(Game game, UUID playerId) {
        EnergySpentOrLostWatcher watcher = game.getState().getWatcher(EnergySpentOrLostWatcher.class);
        return watcher == null ? 0 : watcher.energyLostOrSpent.getOrDefault(playerId, 0);
    }
}