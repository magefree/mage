package mage.game.events;

/**
 * @author TheElk801
 */
public class DamagedPermanentBatchEvent extends DamagedBatchEvent {

    public DamagedPermanentBatchEvent() {
        super(EventType.DAMAGED_PERMANENT_BATCH, DamagedPermanentEvent.class);
    }
}
