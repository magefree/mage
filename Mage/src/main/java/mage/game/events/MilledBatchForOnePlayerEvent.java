package mage.game.events;

/**
 * @author Susucr
 */
public class MilledBatchForOnePlayerEvent extends BatchEvent<MilledCardEvent> {

    public MilledBatchForOnePlayerEvent(MilledCardEvent event) {
        super(EventType.MILLED_CARDS_BATCH_FOR_ONE_PLAYER, false, false, true, event);
    }

}
