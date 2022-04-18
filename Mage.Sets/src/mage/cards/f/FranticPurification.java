
package mage.cards.f;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author magenoxx_at_gmail.com
 */
public final class FranticPurification extends CardImpl {

    public FranticPurification(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");


        // Destroy target enchantment.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetEnchantmentPermanent());

        // Madness {W}
        this.addAbility(new MadnessAbility(new ManaCostsImpl("{W}")));
    }

    private FranticPurification(final FranticPurification card) {
        super(card);
    }

    @Override
    public FranticPurification copy() {
        return new FranticPurification(this);
    }
}
