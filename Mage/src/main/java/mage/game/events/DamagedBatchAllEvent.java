package mage.game.events;

/**
 * @author xenohedron
 */
public class DamagedBatchAllEvent extends BatchEvent<DamagedEvent> {

    public DamagedBatchAllEvent(DamagedEvent firstEvent) {
        super(EventType.DAMAGED_BATCH_FOR_ALL, false, false, false, firstEvent);
    }
}
