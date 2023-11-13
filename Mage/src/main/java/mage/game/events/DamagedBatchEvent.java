package mage.game.events;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public abstract class DamagedBatchEvent extends GameEvent implements BatchGameEvent<DamagedEvent> {

    private final Class<? extends DamagedEvent> damageClazz;
    private final Set<DamagedEvent> events = new HashSet<>();

    protected DamagedBatchEvent(EventType type, Class<? extends DamagedEvent> damageClazz) {
        super(type, null, null, null);
        this.damageClazz = damageClazz;
    }

    @Override
    public Set<DamagedEvent> getEvents() {
        return events;
    }

    @Override
    public Set<UUID> getTargets() {
        return events.stream()
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

    public boolean isCombatDamage() {
        return events.stream().anyMatch(DamagedEvent::isCombatDamage);
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
