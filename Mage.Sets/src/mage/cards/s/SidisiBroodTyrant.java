package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
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

/**
 *
 * @author LevelX2
 */
public final class SidisiBroodTyrant extends CardImpl {

    public SidisiBroodTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.NAGA);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Sidisi, Brood Tyrant enters the battlefield or attacks, put the top three cards of your library into your graveyard.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new MillCardsControllerEffect(3)));

        // Whenever one or more creature cards are put into your graveyard from your library, create a 2/2 black Zombie creature token.
        this.addAbility(new SidisiBroodTyrantTriggeredAbility());
    }

    private SidisiBroodTyrant(final SidisiBroodTyrant card) {
        super(card);
    }

    @Override
    public SidisiBroodTyrant copy() {
        return new SidisiBroodTyrant(this);
    }
}

class SidisiBroodTyrantTriggeredAbility extends TriggeredAbilityImpl {

    public SidisiBroodTyrantTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new ZombieToken()), false);
    }

    public SidisiBroodTyrantTriggeredAbility(final SidisiBroodTyrantTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_GROUP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeGroupEvent zEvent = (ZoneChangeGroupEvent) event;
        if (zEvent != null
                && Zone.LIBRARY == zEvent.getFromZone()
                && Zone.GRAVEYARD == zEvent.getToZone()
                && zEvent.getCards() != null) {
            for (Card card : zEvent.getCards()) {
                if (card != null) {
                    UUID cardOwnerId = card.getOwnerId();
                    if (cardOwnerId != null
                            && card.isOwnedBy(getControllerId())
                            && card.isCreature(game)) {
                        return true;
                    }
                }

            }
        }
        return false;
    }

    @Override
    public SidisiBroodTyrantTriggeredAbility copy() {
        return new SidisiBroodTyrantTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever one or more creature cards are put into your graveyard from your library, create a 2/2 black Zombie creature token.";
    }
}
