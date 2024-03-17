package mage.game.events;

public class DamagedBatchForOnePermanentEvent extends DamagedBatchEvent {

    public DamagedBatchForOnePermanentEvent(DamagedEvent firstEvent) {
        super(GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PERMANENT, DamagedPermanentEvent.class);
        addEvent(firstEvent);
        setTargetId(firstEvent.getTargetId());
    }
}
