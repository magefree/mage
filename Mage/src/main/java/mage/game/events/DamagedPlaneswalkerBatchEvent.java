package mage.game.events;

/**
 * @author TheElk801
 */
public class DamagedPlaneswalkerBatchEvent extends DamagedBatchEvent {

    public DamagedPlaneswalkerBatchEvent() {
        super(GameEvent.EventType.DAMAGED_PLANESWALKER_BATCH, DamagedPlaneswalkerEvent.class);
    }
}
