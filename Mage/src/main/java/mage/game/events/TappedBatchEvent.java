package mage.game.events;

/**
 * @author Susucr
 */
public class TappedBatchEvent extends BatchEvent<TappedEvent> {

    public TappedBatchEvent(TappedEvent firstEvent) {
        super(EventType.TAPPED_BATCH, false, firstEvent);
    }

}
