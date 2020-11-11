package mage.game.events;

/**
 * @author TheElk801
 */
public class DamagedCreatureBatchEvent extends DamagedBatchEvent {

    public DamagedCreatureBatchEvent() {
        super(EventType.DAMAGED_CREATURE_BATCH, DamagedCreatureEvent.class);
    }
}
