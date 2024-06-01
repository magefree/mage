package mage.game.events;

public class DamagedBatchForOnePermanentEvent extends BatchEvent<DamagedPermanentEvent> {

    public DamagedBatchForOnePermanentEvent(DamagedPermanentEvent firstEvent) {
        super(GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PERMANENT, true, false, false, firstEvent);
    }

    public boolean isCombatDamage() {
        return getEvents()
                .stream()
                .anyMatch(DamagedEvent::isCombatDamage);
    }
}
