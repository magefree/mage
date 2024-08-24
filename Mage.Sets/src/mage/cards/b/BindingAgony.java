package mage.cards.b;

import mage.abilities.common.IsDealtDamageAttachedTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageAttachedControllerEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class BindingAgony extends CardImpl {

    public BindingAgony(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));

        // Whenever enchanted creature is dealt damage, Binding Agony deals that much damage to that creature's controller.
        this.addAbility(new IsDealtDamageAttachedTriggeredAbility(
                new DamageAttachedControllerEffect(SavedDamageValue.MUCH), false, "enchanted"
        ));
    }

    private BindingAgony(final BindingAgony card) {
        super(card);
    }

    @Override
    public BindingAgony copy() {
        return new BindingAgony(this);
    }
}
