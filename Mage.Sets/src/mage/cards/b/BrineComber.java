package mage.cards.b;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrineComber extends CardImpl {

    public BrineComber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.secondSideCardClazz = mage.cards.b.BrineboundGift.class;

        // Whenever Brine Comber enters the battlefield or becomes the target of an Aura spell, create a 1/1 white Spirit creature token with flying.
        this.addAbility(new BrineComberTriggeredAbility());

        // Disturb {W}{U}
        this.addAbility(new DisturbAbility(this, "{W}{U}"));
    }

    private BrineComber(final BrineComber card) {
        super(card);
    }

    @Override
    public BrineComber copy() {
        return new BrineComber(this);
    }
}

class BrineComberTriggeredAbility extends TriggeredAbilityImpl {

    BrineComberTriggeredAbility() {
        super(Zone.ALL, new CreateTokenEffect(new SpiritWhiteToken()));
    }

    private BrineComberTriggeredAbility(final BrineComberTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public BrineComberTriggeredAbility copy() {
        return new BrineComberTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                || event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        switch (event.getType()) {
            case ENTERS_THE_BATTLEFIELD:
                return event.getTargetId().equals(getSourceId());
            case TARGETED:
                break;
            default:
                return false;
        }
        if (this.getSourcePermanentIfItStillExists(game) == null
                || !event.getTargetId().equals(getSourceId())) {
            return false;
        }
        Spell spell = game.getSpell(event.getSourceId());
        return spell != null && spell.hasSubtype(SubType.AURA, game);
    }

    @Override
    public String getRule() {
        return "Whenever {this} enters the battlefield or becomes the target " +
                "of an Aura spell, create a 1/1 white Spirit creature token with flying.";
    }
}
