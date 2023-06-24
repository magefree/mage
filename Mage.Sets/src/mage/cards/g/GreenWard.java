package mage.cards.g;

import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.ProtectionAbility;
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
 * @author LoneFox
 */
public final class GreenWard extends CardImpl {

    public GreenWard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Protect));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature has protection from green. This effect doesn't remove Green Ward.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                ProtectionAbility.from(ObjectColor.GREEN), AttachmentType.AURA
        ).setDoesntRemoveItself(true)));
    }

    private GreenWard(final GreenWard card) {
        super(card);
    }

    @Override
    public GreenWard copy() {
        return new GreenWard(this);
    }
}
