package mage.cards.b;

import java.util.UUID;
import mage.abilities.common.DealtDamageAttachedTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageAttachedControllerEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class BindingAgony extends CardImpl {

    public BindingAgony(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));

        // Whenever enchanted creature is dealt damage, Binding Agony deals that much damage to that creature's controller.
        this.addAbility(new DealtDamageAttachedTriggeredAbility(new DamageAttachedControllerEffect(SavedDamageValue.MUCH), false));
    }

    private BindingAgony(final BindingAgony card) {
        super(card);
    }

    @Override
    public BindingAgony copy() {
        return new BindingAgony(this);
    }
}
