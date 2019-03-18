
package mage.game.events;

import java.util.Set;
import java.util.UUID;
import mage.cards.Card;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public class ZoneChangeGroupEvent extends GameEvent {

    private final Zone fromZone;
    private final Zone toZone;
    private final Set<Card> cards;

    public ZoneChangeGroupEvent(Set<Card> cards, UUID sourceId, UUID playerId, Zone fromZone, Zone toZone) {
        super(EventType.ZONE_CHANGE_GROUP, null, sourceId, playerId);
        this.fromZone = fromZone;
        this.toZone = toZone;
        this.cards = cards;
    }

    public Zone getFromZone() {
        return fromZone;
    }

    public Zone getToZone() {
        return toZone;
    }

    public Set<Card> getCards() {
        return cards;
    }

}
