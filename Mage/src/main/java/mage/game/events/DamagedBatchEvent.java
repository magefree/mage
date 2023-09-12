package mage.game.events;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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

    @Override
    @Deprecated // events can store a diff value, so search it from events list instead
    public UUID getTargetId() {
        throw new IllegalStateException("Wrong code usage. Must search value from a getEvents list.");
    }

    @Override
    @Deprecated // events can store a diff value, so search it from events list instead
    public UUID getSourceId() {
        throw new IllegalStateException("Wrong code usage. Must search value from a getEvents list.");
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
            throw new IllegalArgumentException("Wrong code usage. Unknown damage event for a new batch: " + damagedEvent.getClass().getName());
        }
        return event;
    }
}
