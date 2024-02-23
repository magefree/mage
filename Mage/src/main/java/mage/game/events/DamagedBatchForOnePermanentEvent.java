package mage.game.events;

import java.util.UUID;

public class DamagedBatchForOnePermanentEvent extends DamagedBatchEvent {

    public DamagedBatchForOnePermanentEvent(UUID targetId) {
        super(GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PERMANENT, DamagedPermanentEvent.class);
        this.setTargetId(targetId);
    }
}
