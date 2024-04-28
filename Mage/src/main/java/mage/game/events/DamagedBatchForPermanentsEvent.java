package mage.game.events;

/**
 * @author TheElk801
 */
public class DamagedBatchForPermanentsEvent extends BatchEvent<DamagedPermanentEvent> {

    public DamagedBatchForPermanentsEvent(DamagedPermanentEvent firstEvent) {
        super(EventType.DAMAGED_BATCH_FOR_PERMANENTS, false, false, firstEvent);
    }
}
