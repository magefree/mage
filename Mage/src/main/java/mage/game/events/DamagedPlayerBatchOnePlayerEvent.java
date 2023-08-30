package mage.game.events;

import java.util.UUID;

/**
 * @author Susucr
 */
public class DamagedPlayerBatchOnePlayerEvent extends DamagedBatchEvent {

    public DamagedPlayerBatchOnePlayerEvent(UUID playerId) {
        super(EventType.DAMAGED_PLAYER_BATCH_ONE_PLAYER, DamagedPlayerEvent.class);
        this.setPlayerId(playerId);
    }
}
