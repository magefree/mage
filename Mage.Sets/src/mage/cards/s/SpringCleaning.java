
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoIfClashWonEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author Styxo
 */
public final class SpringCleaning extends CardImpl {

    private static final FilterEnchantmentPermanent filter = new FilterEnchantmentPermanent("enchantments your opponents control");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public SpringCleaning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        // Destroy target enchantment. 
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetEnchantmentPermanent());

        // Clash with an opponent. If you win, destroy all enchantments your opponents control. 
        this.getSpellAbility().addEffect(new DoIfClashWonEffect(new DestroyAllEffect(filter)));
    }

    private SpringCleaning(final SpringCleaning card) {
        super(card);
    }

    @Override
    public SpringCleaning copy() {
        return new SpringCleaning(this);
    }
}
