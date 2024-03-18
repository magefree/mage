package mage.game.events;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ZoneChangeBatchEvent extends GameEvent implements BatchGameEvent<ZoneChangeEvent> {

    private final Set<ZoneChangeEvent> events = new HashSet<>();

    public ZoneChangeBatchEvent() {
        super(EventType.ZONE_CHANGE_BATCH, null, null, null);
    }

    @Override
    public Set<ZoneChangeEvent> getEvents() {
        return events;
    }

    @Override
    public Set<UUID> getTargets() {
        return events
                .stream()
                .map(GameEvent::getTargetId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @Override
    public int getAmount() {
        return events
                .stream()
                .mapToInt(GameEvent::getAmount)
                .sum();
    }

    @Override
    @Deprecated // events can store a diff value, so search it from events list instead
    public UUID getTargetId() {
        throw new IllegalStateException("Wrong code usage. Must search value from a getEvents list or use CardUtil.getEventTargets(event)");
    }

    @Override
    @Deprecated // events can store a diff value, so search it from events list instead
    public UUID getSourceId() {
        throw new IllegalStateException("Wrong code usage. Must search value from a getEvents list.");
    }

    public void addEvent(ZoneChangeEvent event) {
        this.events.add(event);
    }
}
