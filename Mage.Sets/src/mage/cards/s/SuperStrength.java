package mage.cards.s;

import java.util.UUID;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class SuperStrength extends CardImpl {

    public SuperStrength(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets +4/+4 and has trample and ward {1}.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(4, 4));
        ability.addEffect(new GainAbilityAttachedEffect(
            TrampleAbility.getInstance(), AttachmentType.AURA
        ).setText(" and has trample"));
        ability.addEffect(new GainAbilityAttachedEffect(
                new WardAbility(new GenericManaCost(1), false), AttachmentType.AURA
        ).setText(" and ward {1}"));
        this.addAbility(ability);
    }

    private SuperStrength(final SuperStrength card) {
        super(card);
    }

    @Override
    public SuperStrength copy() {
        return new SuperStrength(this);
    }
}
