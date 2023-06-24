package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
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
 * @author North
 */
public final class SavageSilhouette extends CardImpl {

    public SavageSilhouette(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets +2/+2 and has "{1}{G}: Regenerate this creature."
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(
                2, 2, Duration.WhileOnBattlefield
        ));
        ability.addEffect(new GainAbilityAttachedEffect(new SimpleActivatedAbility(
                new RegenerateSourceEffect(), new ManaCostsImpl<>("{1}{G}")
        ), AttachmentType.AURA).setText("and has \"{1}{G}: Regenerate this creature.\""));
        this.addAbility(ability);
    }

    private SavageSilhouette(final SavageSilhouette card) {
        super(card);
    }

    @Override
    public SavageSilhouette copy() {
        return new SavageSilhouette(this);
    }
}
