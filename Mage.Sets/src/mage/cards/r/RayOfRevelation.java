
package mage.cards.r;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author North
 */
public final class RayOfRevelation extends CardImpl {

    public RayOfRevelation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");


        // Destroy target enchantment.
        this.getSpellAbility().addTarget(new TargetEnchantmentPermanent());
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        // Flashback {G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{G}")));
    }

    private RayOfRevelation(final RayOfRevelation card) {
        super(card);
    }

    @Override
    public RayOfRevelation copy() {
        return new RayOfRevelation(this);
    }
}
