package mage.game.events;

import mage.cards.Card;
import mage.constants.Zone;
import mage.game.permanent.PermanentToken;

import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX2
 */
public class ZoneChangeGroupEvent extends GameEvent {

    private final Zone fromZone;
    private final Zone toZone;
    private final Set<Card> cards;
    private final Set<PermanentToken> tokens;

    public ZoneChangeGroupEvent(Set<Card> cards, Set<PermanentToken> tokens, UUID sourceId, UUID playerId, Zone fromZone, Zone toZone) {
        super(EventType.ZONE_CHANGE_GROUP, null, sourceId, playerId);
        this.fromZone = fromZone;
        this.toZone = toZone;
        this.cards = cards;
        this.tokens = tokens;
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

    public Set<PermanentToken> getTokens() {
        return tokens;
    }

}
