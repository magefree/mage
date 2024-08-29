package mage.game.events;

public class SacrificedPermanentBatchEvent extends BatchEvent<GameEvent> {

    public SacrificedPermanentBatchEvent(GameEvent sacrificedEvent) {
        super(EventType.SACRIFICED_PERMANENT_BATCH, false, false, false, sacrificedEvent);
    }
}
