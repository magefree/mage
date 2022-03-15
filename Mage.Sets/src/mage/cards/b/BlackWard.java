package mage.cards.b;

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
public final class BlackWard extends CardImpl {

    public BlackWard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Protect));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Enchanted creature has protection from black. This effect doesn't remove Black Ward.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                ProtectionAbility.from(ObjectColor.BLACK), AttachmentType.AURA
        ).setDoesntRemoveItself(true)));
    }

    private BlackWard(final BlackWard card) {
        super(card);
    }

    @Override
    public BlackWard copy() {
        return new BlackWard(this);
    }
}
