package mage.game.events;

/**
 * @author TheElk801
 */
public class DamagedBatchForPlayersEvent extends DamagedBatchEvent {

    public DamagedBatchForPlayersEvent() {
        super(GameEvent.EventType.DAMAGED_BATCH_FOR_PLAYERS, DamagedPlayerEvent.class);
    }
}
