package mage.game.events;

/**
 * Batch all simultaneous damage events dealt by a single source.
 *
 * @author Susucr
 */
public class DamagedBatchBySourceEvent extends BatchEvent<DamagedEvent> {

    public DamagedBatchBySourceEvent(DamagedEvent firstEvent) {
        super(EventType.DAMAGED_BATCH_BY_SOURCE, false, true, false, firstEvent);
    }

    public boolean isCombatDamage() {
        return getEvents()
                .stream()
                .anyMatch(DamagedEvent::isCombatDamage);
    }
}
