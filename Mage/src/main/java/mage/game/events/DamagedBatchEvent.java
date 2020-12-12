package mage.game.events;

import java.util.HashSet;
import java.util.Set;

/**
 * @author TheElk801
 */
public abstract class DamagedBatchEvent extends GameEvent {

    private final Class<? extends DamagedEvent> damageClazz;
    private final Set<DamagedEvent> events = new HashSet<>();

    public DamagedBatchEvent(EventType type, Class<? extends DamagedEvent> damageClazz) {
        super(GameEvent.EventType.DAMAGED_PLAYER_BATCH, null, null, null);
        this.damageClazz = damageClazz;
    }

    public Set<DamagedEvent> getEvents() {
        return events;
    }

    public void addEvent(DamagedEvent event) {
        this.events.add(event);
    }

    public Class<? extends DamagedEvent> getDamageClazz() {
        return damageClazz;
    }

    public static DamagedBatchEvent makeEvent(DamagedEvent damagedEvent) {
        DamagedBatchEvent event = null;
        if (damagedEvent instanceof DamagedPlayerEvent) {
            event = new DamagedPlayerBatchEvent();
            event.addEvent(damagedEvent);
        } else if (damagedEvent instanceof DamagedCreatureEvent) {
            event = new DamagedCreatureBatchEvent();
            event.addEvent(damagedEvent);
        } else if (damagedEvent instanceof DamagedPlaneswalkerEvent) {
            event = new DamagedPlaneswalkerBatchEvent();
            event.addEvent(damagedEvent);
        }
        return event;
    }
}
