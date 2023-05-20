package mage.cards.b;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
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
public final class BrineComber extends TransformingDoubleFacedCard {

    public BrineComber(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SPIRIT}, "{1}{W}{U}",
                "Brinebound Gift",
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.AURA}, "UW"
        );
        this.getLeftHalfCard().setPT(1, 1);

        // Whenever Brine Comber enters the battlefield or becomes the target of an Aura spell, create a 1/1 white Spirit creature token with flying.
        this.getLeftHalfCard().addAbility(new BrineComberTriggeredAbility());

        // Disturb {W}{U}
        this.getLeftHalfCard().addAbility(new DisturbAbility(this, "{W}{U}"));

        // Brinebound Gift
        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getRightHalfCard().getSpellAbility().addTarget(auraTarget);
        this.getRightHalfCard().getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.getRightHalfCard().addAbility(new EnchantAbility(auraTarget));

        // Whenever Brinebound Gift enters the battlefield or enchanted creature becomes the target of an Aura spell, create a 1/1 white Spirit creature token with flying.
        this.getRightHalfCard().addAbility(new BrineboundGiftTriggeredAbility());

        // If Brinebound Gift would be put into a graveyard from anywhere, exile it instead.
        this.getRightHalfCard().addAbility(DisturbAbility.makeExileAbility());
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
