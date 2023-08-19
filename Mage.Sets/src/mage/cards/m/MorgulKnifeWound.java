package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DoUnlessControllerPaysEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MorgulKnifeWound extends CardImpl {

    public MorgulKnifeWound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets -3/-0 and has "At the beginning of your upkeep, exile this creature unless you pay 2 life."
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(-3, 0));
        ability.addEffect(new GainAbilityAttachedEffect(new BeginningOfUpkeepTriggeredAbility(
                new DoUnlessControllerPaysEffect(
                        new ExileSourceEffect(),
                        new PayLifeCost(2),
                        "Pay 2 life to prevent {this} from being exiled?"
                ).setText("exile {this} unless you pay 2 life"), TargetController.YOU, false
        ), AttachmentType.AURA).setText("and has \"At the beginning of your upkeep, exile this creature unless you pay 2 life.\""));
        this.addAbility(ability);
    }

    private MorgulKnifeWound(final MorgulKnifeWound card) {
        super(card);
    }

    @Override
    public MorgulKnifeWound copy() {
        return new MorgulKnifeWound(this);
    }
}
