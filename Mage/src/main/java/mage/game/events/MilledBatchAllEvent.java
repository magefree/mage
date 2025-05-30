package mage.game.events;

/**
 * @author Susucr
 */
public class MilledBatchAllEvent extends BatchEvent<MilledCardEvent> {

    public MilledBatchAllEvent(MilledCardEvent event) {
        super(EventType.MILLED_CARDS_BATCH_FOR_ALL, false, false, false, event);
    }

}
