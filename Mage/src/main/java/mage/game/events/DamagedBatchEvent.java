package mage.game.events;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public abstract class DamagedBatchEvent extends GameEvent {

    private final Class<? extends DamagedEvent> damageClazz;
    private final Set<DamagedEvent> events = new HashSet<>();

    public DamagedBatchEvent(EventType type, Class<? extends DamagedEvent> damageClazz) {
        super(type, null, null, null);
        this.damageClazz = damageClazz;
    }

    public Set<DamagedEvent> getEvents() {
        return events;
    }

    @Override
    public int getAmount() {
        return events
                .stream()
                .mapToInt(GameEvent::getAmount)
                .sum();
    }

    public Set<UUID> getTargetIds() {
        return events
                .stream()
                .map(GameEvent::getTargetId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @Override
    @Deprecated // events can store a diff value, so search it from events list instead
    public UUID getTargetId() {
        // TODO: replace it by getTargetIds or seearch in getEvents, enable exception
        //throw new IllegalStateException("Wrong code usage. Must search batch value from a list");
        return events
                .stream()
                .map(GameEvent::getTargetId)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    public Set<UUID> getSourceIds() {
        return events
                .stream()
                .map(GameEvent::getSourceId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @Override
    @Deprecated // events can store a diff value, so search it from events list instead
    public UUID getSourceId() {
        // TODO: replace it by getSourceIds or seearch in getEvents, enable exception
        //throw new IllegalStateException("Wrong code usage. Must search batch value from a list");
        return events
                .stream()
                .map(GameEvent::getSourceId)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    public void addEvent(DamagedEvent event) {
        this.events.add(event);
    }

    public Class<? extends DamagedEvent> getDamageClazz() {
        return damageClazz;
    }

    public static DamagedBatchEvent makeEvent(DamagedEvent damagedEvent) {
        DamagedBatchEvent event;
        if (damagedEvent instanceof DamagedPlayerEvent) {
            event = new DamagedBatchForPlayersEvent();
            event.addEvent(damagedEvent);
        } else if (damagedEvent instanceof DamagedPermanentEvent) {
            event = new DamagedBatchForPermanentsEvent();
            event.addEvent(damagedEvent);
        } else {
            event = null;
        }
        return event;
    }
}
