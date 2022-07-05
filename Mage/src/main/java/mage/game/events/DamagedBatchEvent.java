package mage.game.events;

import java.util.HashSet;
import java.util.Objects;
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
    public UUID getTargetId() {
        return events
                .stream()
                .map(GameEvent::getTargetId)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    @Override
    public UUID getSourceId() {
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
            event = new DamagedPlayerBatchEvent();
            event.addEvent(damagedEvent);
        } else if (damagedEvent instanceof DamagedPermanentEvent) {
            event = new DamagedPermanentBatchEvent();
            event.addEvent(damagedEvent);
        } else {
            event = null;
        }
        return event;
    }
}
