package mage.cards.d;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeGroupEvent;
import mage.game.permanent.token.BatToken;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DesecratedTomb extends CardImpl {

    public DesecratedTomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Whenever one or more creature cards leave your graveyard, create a 1/1 black Bat creature token with flying.
        this.addAbility(new DesecratedTombTriggeredAbility());
    }

    private DesecratedTomb(final DesecratedTomb card) {
        super(card);
    }

    @Override
    public DesecratedTomb copy() {
        return new DesecratedTomb(this);
    }
}

class DesecratedTombTriggeredAbility extends TriggeredAbilityImpl {

    DesecratedTombTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new BatToken()), false);
    }

    private DesecratedTombTriggeredAbility(final DesecratedTombTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_GROUP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeGroupEvent zEvent = (ZoneChangeGroupEvent) event;
        return zEvent != null
                && Zone.GRAVEYARD == zEvent.getFromZone()
                && Zone.GRAVEYARD != zEvent.getToZone()
                && zEvent.getCards() != null
                && zEvent.getCards()
                .stream()
                .filter(Objects::nonNull)
                .filter(card -> card.isCreature(game))
                .map(Card::getOwnerId)
                .anyMatch(getControllerId()::equals);
    }

    @Override
    public DesecratedTombTriggeredAbility copy() {
        return new DesecratedTombTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever one or more creature cards leave your graveyard, " +
                "create a 1/1 black Bat creature token with flying.";
    }
}
