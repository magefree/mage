package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CountersWatcher extends Watcher {

    private final Map<UUID, Map<CounterType, Integer>> countersPutOnCard = new HashMap<>();
    private final Map<UUID, Map<CounterType, Integer>> countersRemovedFromCard = new HashMap<>();

    public CountersWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        CounterType counterType = CounterType.findByName(event.getData());
        int amount = event.getAmount();
        UUID cardId = event.getTargetId();
        if (event.getType() == GameEvent.EventType.COUNTER_ADDED
                || event.getType() == GameEvent.EventType.COUNTERS_ADDED) {
            countersPutOnCard.computeIfAbsent(cardId, key -> new HashMap<>());
            countersPutOnCard.computeIfPresent(cardId, (key, value) -> {
                value.merge(counterType, amount, Integer::sum);
                return value;
            });
        }
        if (event.getType() == GameEvent.EventType.COUNTER_REMOVED
                || event.getType() == GameEvent.EventType.COUNTERS_REMOVED) {
            countersRemovedFromCard.computeIfAbsent(cardId, key -> new HashMap<>());
            countersRemovedFromCard.computeIfPresent(cardId, (key, value) -> {
                value.merge(counterType, amount, Integer::sum);
                return value;
            });
        }
    }

    @Override
    public void reset() {
        super.reset();
        countersPutOnCard.clear();
        countersRemovedFromCard.clear();
    }

    private boolean counterTypeHasBeenAddedOrRemovedFromCard(CounterType counterType, Map<UUID, Map<CounterType, Integer>> cardsMap) {
        return cardsMap.values()
                .stream()
                .anyMatch((map) -> map.keySet()
                        .stream()
                        .anyMatch(counterType1 -> counterType1.equals(counterType)));
    }

    public boolean counterTypeHasBeenAddedToCard(CounterType counterType) {
        return counterTypeHasBeenAddedOrRemovedFromCard(counterType, countersPutOnCard);
    }

    public boolean counterTypeHasBeenAddedToCardControlledByPlayer(CounterType counterType, UUID controllerId, Game game) {
        Map<UUID, Map<CounterType, Integer>> countersPutOnCardControlledByPlayer = new HashMap<>();
        countersPutOnCard.forEach(((uuid, map) -> {
            Permanent permanent = game.getPermanent(uuid);
            if (permanent != null) {
                if (permanent.getControllerId().equals(controllerId)) {
                    countersPutOnCardControlledByPlayer.put(uuid, map);
                }
            }
        }));
        return counterTypeHasBeenAddedOrRemovedFromCard(counterType, countersPutOnCardControlledByPlayer);
    }

    public boolean counterTypeHasBeenRemovedFromCard(CounterType counterType) {
        return counterTypeHasBeenAddedOrRemovedFromCard(counterType, countersRemovedFromCard);
    }

    public boolean counterTypeHasBeenRemovedFromCardControlledByPlayer(CounterType counterType, UUID controllerId, Game game) {
        Map<UUID, Map<CounterType, Integer>> countersRemovedFromCardControlledByPlayer = new HashMap<>();
        countersRemovedFromCard.forEach(((uuid, map) -> {
            Permanent permanent = game.getPermanent(uuid);
            if (permanent != null) {
                if (permanent.getControllerId().equals(controllerId)) {
                    countersRemovedFromCardControlledByPlayer.put(uuid, map);
                }
            }
        }));
        return counterTypeHasBeenAddedOrRemovedFromCard(counterType, countersRemovedFromCardControlledByPlayer);
    }
}
