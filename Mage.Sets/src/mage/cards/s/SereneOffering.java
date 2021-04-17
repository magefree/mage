
package mage.cards.s;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.TargetManaValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author LoneFox
 */
public final class SereneOffering extends CardImpl {

    public SereneOffering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // Destroy target enchantment. You gain life equal to its converted mana cost.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        Effect effect = new GainLifeEffect(TargetManaValue.instance);
        effect.setText("You gain life equal to its mana value");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetEnchantmentPermanent());
    }

    private SereneOffering(final SereneOffering card) {
        super(card);
    }

    @Override
    public SereneOffering copy() {
        return new SereneOffering(this);
    }
}
