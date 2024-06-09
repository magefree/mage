package mage.game.events;

/**
 * @author TheElk801
 */
public class DamagedBatchForPlayersEvent extends BatchEvent<DamagedPlayerEvent> {

    public DamagedBatchForPlayersEvent(DamagedPlayerEvent firstEvent) {
        super(GameEvent.EventType.DAMAGED_BATCH_FOR_PLAYERS, false, false, false, firstEvent);
    }
}
