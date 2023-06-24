package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.PutIntoGraveFromAnywhereSourceAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.game.stack.Spell;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrineboundGift extends CardImpl {

    public BrineboundGift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "");

        this.subtype.add(SubType.AURA);
        this.color.setWhite(true);
        this.color.setBlue(true);
        this.nightCard = true;

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Whenever Brinebound Gift enters the battlefield or enchanted creature becomes the target of an Aura spell, create a 1/1 white Spirit creature token with flying.
        this.addAbility(new BrineboundGiftTriggeredAbility());

        // If Brinebound Gift would be put into a graveyard from anywhere, exile it instead.
        this.addAbility(new PutIntoGraveFromAnywhereSourceAbility(new ExileSourceEffect().setText("exile it instead")));
    }

    private BrineboundGift(final BrineboundGift card) {
        super(card);
    }

    @Override
    public BrineboundGift copy() {
        return new BrineboundGift(this);
    }
}

class BrineboundGiftTriggeredAbility extends TriggeredAbilityImpl {

    BrineboundGiftTriggeredAbility() {
        super(Zone.ALL, new CreateTokenEffect(new SpiritWhiteToken()));
    }

    private BrineboundGiftTriggeredAbility(final BrineboundGiftTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public BrineboundGiftTriggeredAbility copy() {
        return new BrineboundGiftTriggeredAbility(this);
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
        Permanent permanent = this.getSourcePermanentOrLKI(game);
        if (permanent == null || !event.getTargetId().equals(permanent.getAttachedTo())) {
            return false;
        }
        Spell spell = game.getSpell(event.getSourceId());
        return spell != null && spell.hasSubtype(SubType.AURA, game);
    }

    @Override
    public String getRule() {
        return "Whenever {this} enters the battlefield or enchanted creature becomes the target " +
                "of an Aura spell, create a 1/1 white Spirit creature token with flying.";
    }
}
