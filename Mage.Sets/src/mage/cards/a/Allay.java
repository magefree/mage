
package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author fireshoes
 */
public final class Allay extends CardImpl {

    public Allay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // Buyback {3}
        this.addAbility(new BuybackAbility("{3}"));
        
        // Destroy target enchantment.
        this.getSpellAbility().addTarget(new TargetEnchantmentPermanent());
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
    }

    private Allay(final Allay card) {
        super(card);
    }

    @Override
    public Allay copy() {
        return new Allay(this);
    }
}
