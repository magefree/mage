package mage.game.events;

/**
 * @author Susucr
 */
public class DamagedBatchForOnePlayerEvent extends DamagedBatchEvent {

    public DamagedBatchForOnePlayerEvent(DamagedEvent firstEvent) {
        super(EventType.DAMAGED_BATCH_FOR_ONE_PLAYER, DamagedPlayerEvent.class);
        setPlayerId(firstEvent.getPlayerId());
        addEvent(firstEvent);
    }
}
