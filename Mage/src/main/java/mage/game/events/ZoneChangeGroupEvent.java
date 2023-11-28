package mage.game.events;

import mage.abilities.Ability;
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
    /* added this */ Ability source;

    public ZoneChangeGroupEvent(Set<Card> cards, Set<PermanentToken> tokens, UUID sourceId, Ability source, UUID playerId, Zone fromZone, Zone toZone) {
        super(GameEvent.EventType.ZONE_CHANGE_GROUP, null, null, playerId);
        this.fromZone = fromZone;
        this.toZone = toZone;
        this.cards = cards;
        this.tokens = tokens;
        this.source = source;
    }

    public Zone getFromZone() {
        return fromZone;
    }

    public Zone getToZone() {
        return toZone;
    }

    public boolean isDiesEvent() {
        return (toZone == Zone.GRAVEYARD && fromZone == Zone.BATTLEFIELD);
    }

    public Set<Card> getCards() {
        return cards;
    }

    public Set<PermanentToken> getTokens() {
        return tokens;
    }

    public Ability getSource() {
        return source;
    }

}
