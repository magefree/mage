package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.BecomesTargetAttachedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromAnywhereSourceAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
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
public final class BrineboundGift extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an Aura spell");

    static {
        filter.add(AuraSpellPredicate.instance);
    }

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
        this.addAbility(new OrTriggeredAbility(Zone.ALL, new CreateTokenEffect(new SpiritWhiteToken()), false,
                "Whenever {this} enters the battlefield or enchanted creature becomes the target of an Aura spell, ",
                new EntersBattlefieldTriggeredAbility(null),
                new BecomesTargetAttachedTriggeredAbility(null, filter, SetTargetPointer.NONE, false)));

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
