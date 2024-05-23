package mage.game.events;

public class ZoneChangeBatchEvent extends BatchEvent<ZoneChangeEvent> {

    public ZoneChangeBatchEvent() {
        super(EventType.ZONE_CHANGE_BATCH);
    }
}
