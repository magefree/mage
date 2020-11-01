package mage.cards.t;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeGroupEvent;
import mage.game.permanent.token.ZombieToken;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TormodTheDesecrator extends CardImpl {

    public TormodTheDesecrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Whenever one or more cards leave your graveyard, create a tapped 2/2 black Zombie creature token.
        this.addAbility(new TormodTheDesecratorTriggeredAbility());

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private TormodTheDesecrator(final TormodTheDesecrator card) {
        super(card);
    }

    @Override
    public TormodTheDesecrator copy() {
        return new TormodTheDesecrator(this);
    }
}

class TormodTheDesecratorTriggeredAbility extends TriggeredAbilityImpl {

    TormodTheDesecratorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new ZombieToken(), 1, true, false), false);
    }

    private TormodTheDesecratorTriggeredAbility(final TormodTheDesecratorTriggeredAbility ability) {
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
                .map(Card::getOwnerId)
                .anyMatch(getControllerId()::equals);
    }

    @Override
    public TormodTheDesecratorTriggeredAbility copy() {
        return new TormodTheDesecratorTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever one or more cards leave your graveyard, " +
                "create a tapped 2/2 black Zombie creature token.";
    }
}
