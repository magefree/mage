package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CrystallineArmor extends CardImpl {

    public CrystallineArmor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets +1/+1 for each land you control and has trample.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(
                LandsYouControlCount.instance, LandsYouControlCount.instance
        ).setText("enchanted creature gets +1/+1 for each land you control"));
        ability.addEffect(new GainAbilityAttachedEffect(
                TrampleAbility.getInstance(), AttachmentType.AURA
        ).setText("and has trample"));
        this.addAbility(ability);
    }

    private CrystallineArmor(final CrystallineArmor card) {
        super(card);
    }

    @Override
    public CrystallineArmor copy() {
        return new CrystallineArmor(this);
    }
}
