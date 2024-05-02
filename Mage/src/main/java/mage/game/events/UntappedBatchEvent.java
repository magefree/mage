package mage.game.events;

/**
 * @author Susucr
 */
public class UntappedBatchEvent extends BatchEvent<UntappedEvent> {

    public UntappedBatchEvent(UntappedEvent firstEvent) {
        super(EventType.UNTAPPED_BATCH, false, false, firstEvent);
    }

}
