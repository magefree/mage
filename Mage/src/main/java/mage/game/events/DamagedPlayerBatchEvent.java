package mage.game.events;

/**
 * @author TheElk801
 */
public class DamagedPlayerBatchEvent extends DamagedBatchEvent {

    public DamagedPlayerBatchEvent() {
        super(GameEvent.EventType.DAMAGED_PLAYER_BATCH, DamagedPlayerEvent.class);
    }
}
