
package mage.cards.w;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author markedagain
 */
public final class WipeClean extends CardImpl {

    public WipeClean(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // Exile target enchantment.
        this.getSpellAbility().addTarget(new TargetEnchantmentPermanent());
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        // Cycling {3}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{3}")));
    }

    private WipeClean(final WipeClean card) {
        super(card);
    }

    @Override
    public WipeClean copy() {
        return new WipeClean(this);
    }
}
