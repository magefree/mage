package mage.cards.b;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrinelinTheMoonKraken extends CardImpl {

    public BrinelinTheMoonKraken(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KRAKEN);
        this.power = new MageInt(6);
        this.toughness = new MageInt(8);

        // When Brinelin, the Moon Kraken enters the battlefield or whenever you cast a spell with converted mana cost 6 or greater, you may return target nonland permanent to its owner's hand.
        this.addAbility(new BrinelinTheMoonKrakenTriggeredAbility());

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private BrinelinTheMoonKraken(final BrinelinTheMoonKraken card) {
        super(card);
    }

    @Override
    public BrinelinTheMoonKraken copy() {
        return new BrinelinTheMoonKraken(this);
    }
}

class BrinelinTheMoonKrakenTriggeredAbility extends TriggeredAbilityImpl {

    BrinelinTheMoonKrakenTriggeredAbility() {
        super(Zone.ALL, new ReturnToHandTargetEffect(), true);
        this.addTarget(new TargetNonlandPermanent());
    }

    private BrinelinTheMoonKrakenTriggeredAbility(final BrinelinTheMoonKrakenTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST
                || event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        switch (event.getType()) {
            case SPELL_CAST:
                if (!event.getPlayerId().equals(this.getControllerId())
                        || game.getPermanent(getSourceId()) == null) {
                    return false;
                }
                Spell spell = game.getSpellOrLKIStack(event.getTargetId());
                return spell != null && spell.getManaValue() >= 6;
            case ENTERS_THE_BATTLEFIELD:
                return event.getTargetId().equals(getSourceId());
            default:
                return false;
        }
    }

    @Override
    public String getRule() {
        return "When {this} enters the battlefield or whenever you cast a spell with mana value " +
                "6 or greater, you may return target nonland permanent to its owner's hand.";
    }

    @Override
    public BrinelinTheMoonKrakenTriggeredAbility copy() {
        return new BrinelinTheMoonKrakenTriggeredAbility(this);
    }
}
