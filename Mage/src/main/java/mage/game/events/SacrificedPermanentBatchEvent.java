package mage.game.events;

public class SacrificedPermanentBatchEvent extends BatchEvent<SacrificedPermanentEvent> {

    public SacrificedPermanentBatchEvent(SacrificedPermanentEvent sacrificedPermanentEvent) {
        super(EventType.SACRIFICED_PERMANENT_BATCH, false, false, false, sacrificedPermanentEvent);
    }
}
