package mage.game.events;

/**
 * @author Susucr
 */
public class LifeLostBatchForOnePlayerEvent extends BatchEvent<LifeLostEvent> {

    public LifeLostBatchForOnePlayerEvent(LifeLostEvent firstEvent) {
        super(EventType.LOST_LIFE_BATCH_FOR_ONE_PLAYER, true, false, false, firstEvent);
    }
}
