

package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllControlledTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.target.TargetPlayer;

/**
 *
 * @author L_J
 */
public final class PrimevalLight extends CardImpl {

    public PrimevalLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}");

        // Destroy all enchantments target player controls.
        this.getSpellAbility().addEffect(new DestroyAllControlledTargetEffect(new FilterEnchantmentPermanent("enchantments")));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public PrimevalLight(final PrimevalLight card) {
        super(card);
    }

    @Override
    public PrimevalLight copy() {
        return new PrimevalLight(this);
    }

}
