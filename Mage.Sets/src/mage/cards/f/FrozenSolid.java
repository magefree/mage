
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DestroyAttachedToEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class FrozenSolid extends CardImpl {

    public FrozenSolid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // Enchanted creature doesn't untap during its controller's untap step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepEnchantedEffect()));
        // When enchanted creature is dealt damage, destroy it.
        this.addAbility(new DealtDamageAttachedTriggeredAbility(new DestroyAttachedToEffect("it"), false));
    }

    private FrozenSolid(final FrozenSolid card) {
        super(card);
    }

    @Override
    public FrozenSolid copy() {
        return new FrozenSolid(this);
    }
}
