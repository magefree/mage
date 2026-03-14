package mage.cards.b;

import mage.abilities.common.BecomesTargetAttachedTriggeredAbility;
import mage.abilities.common.BecomesTargetSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.predicate.other.AuraSpellPredicate;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrineComber extends TransformingDoubleFacedCard {

    private static final FilterSpell filter = new FilterSpell("an Aura spell");

    static {
        filter.add(AuraSpellPredicate.instance);
    }

    public BrineComber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SPIRIT}, "{1}{W}{U}",
                "Brinebound Gift",
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.AURA}, "WU");

        // Brine Comber
        this.getLeftHalfCard().setPT(1, 1);

        // Whenever Brine Comber enters the battlefield or becomes the target of an Aura spell, create a 1/1 white Spirit creature token with flying.
        this.getLeftHalfCard().addAbility(new OrTriggeredAbility(Zone.ALL, new CreateTokenEffect(new SpiritWhiteToken()), false,
                "Whenever {this} enters or becomes the target of an Aura spell, ",
                new EntersBattlefieldTriggeredAbility(null),
                new BecomesTargetSourceTriggeredAbility(null, filter)));


        // Brinebound Gift
        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getRightHalfCard().getSpellAbility().addTarget(auraTarget);
        this.getRightHalfCard().getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.getRightHalfCard().addAbility(new EnchantAbility(auraTarget));

        // Disturb {W}{U}
        // needs to be added after enchant ability is set for target
        this.getLeftHalfCard().addAbility(new DisturbAbility(this, "{W}{U}"));

        // Whenever Brinebound Gift enters the battlefield or enchanted creature becomes the target of an Aura spell, create a 1/1 white Spirit creature token with flying.
        this.getRightHalfCard().addAbility(new OrTriggeredAbility(Zone.ALL, new CreateTokenEffect(new SpiritWhiteToken()), false,
                "Whenever {this} enters or enchanted creature becomes the target of an Aura spell, ",
                new EntersBattlefieldTriggeredAbility(null),
                new BecomesTargetAttachedTriggeredAbility(null, filter, SetTargetPointer.NONE, false)));

        // If Brinebound Gift would be put into a graveyard from anywhere, exile it instead.
        this.getRightHalfCard().addAbility(DisturbAbility.makeBackAbility());
    }

    private BrineComber(final BrineComber card) {
        super(card);
    }

    @Override
    public BrineComber copy() {
        return new BrineComber(this);
    }
}
