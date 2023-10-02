package mage.game.events;

/**
 * @author TheElk801
 */
public class DamagedBatchForPermanentsEvent extends DamagedBatchEvent {

    public DamagedBatchForPermanentsEvent() {
        super(EventType.DAMAGED_BATCH_FOR_PERMANENTS, DamagedPermanentEvent.class);
    }
}
