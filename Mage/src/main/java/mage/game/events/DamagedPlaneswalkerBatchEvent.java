package mage.game.events;

/**
 * @author TheElk801
 */
public class DamagedPlaneswalkerBatchEvent extends DamagedBatchEvent {

    public DamagedPlaneswalkerBatchEvent() {
        super(EventType.DAMAGED_PLANESWALKER_BATCH, DamagedPlaneswalkerEvent.class);
    }
}
