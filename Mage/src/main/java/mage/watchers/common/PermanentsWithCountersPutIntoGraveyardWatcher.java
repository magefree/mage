package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.*;
import java.util.stream.Collectors;

public class PermanentsWithCountersPutIntoGraveyardWatcher extends Watcher {

    private final Map<UUID, Set<CounterType>> permanentsWithCountersPutIntoGraveyard = new HashMap<>();

    public PermanentsWithCountersPutIntoGraveyardWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ZONE_CHANGE
                || !Zone.GRAVEYARD.match(((ZoneChangeEvent) event).getToZone())) {
            return;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (permanent != null) {
            Counters counters = permanent.getCounters(game);
            if (counters.isEmpty()) {
                return;
            }
            permanentsWithCountersPutIntoGraveyard.computeIfAbsent(event.getTargetId(), key -> new HashSet<>());
            permanentsWithCountersPutIntoGraveyard.get(event.getTargetId()).addAll(
                    counters.values()
                            .stream()
                            .map(counter -> CounterType.findByName(counter.getName()))
                            .collect(Collectors.toSet())
            );
        }
    }

    @Override
    public void reset() {
        super.reset();
        permanentsWithCountersPutIntoGraveyard.clear();
    }

    public boolean permanentWithCounterWasPutIntoGraveyard(CounterType counterType) {
        return permanentsWithCountersPutIntoGraveyard.values()
                .stream()
                .anyMatch(set -> set.stream().anyMatch(counterType1 -> counterType1.equals(counterType)));
    }
}
