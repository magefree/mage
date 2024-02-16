package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CantBeTargetedAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author dragonfyre23
 */

public final class SpectralShield extends CardImpl {

    public SpectralShield(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature get's +0/+2
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(0, 2, Duration.WhileOnBattlefield)));

        // Enchanted creature can't be the target of spells.
        CantBeTargetedAttachedEffect cantTargetEffect = new CantBeTargetedAttachedEffect(new FilterSpell("spells"), Duration.WhileOnBattlefield, AttachmentType.AURA, TargetController.ANY);
        Ability ability2 = new SimpleStaticAbility(Zone.BATTLEFIELD, cantTargetEffect);
        this.addAbility(ability2);


    }

    private SpectralShield(final mage.cards.s.SpectralShield card) {
        super(card);
    }

    @Override
    public mage.cards.s.SpectralShield copy() {
        return new mage.cards.s.SpectralShield(this);
    }
}
