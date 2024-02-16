package mage.game.events;

import java.util.UUID;

/**
 * @author Susucr
 */
public class DamagedBatchForOnePlayerEvent extends DamagedBatchEvent {

    public DamagedBatchForOnePlayerEvent(UUID playerId) {
        super(EventType.DAMAGED_BATCH_FOR_ONE_PLAYER, DamagedPlayerEvent.class);
        this.setPlayerId(playerId);
    }
}
