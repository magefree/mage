package mage.game.events;

/**
 * @author Susucr
 */
public class DamagedBatchForOnePlayerEvent extends BatchEvent<DamagedPlayerEvent> {

    public DamagedBatchForOnePlayerEvent(DamagedPlayerEvent firstEvent) {
        super(EventType.DAMAGED_BATCH_FOR_ONE_PLAYER, true, firstEvent);
    }

    public boolean isCombatDamage() {
        return getEvents()
                .stream()
                .anyMatch(DamagedEvent::isCombatDamage);
    }
}
