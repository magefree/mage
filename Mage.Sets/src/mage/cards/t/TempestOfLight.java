
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterEnchantmentPermanent;

/**
 *
 * @author Loki
 */
public final class TempestOfLight extends CardImpl {

    public TempestOfLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");

        this.getSpellAbility().addEffect(new DestroyAllEffect(new FilterEnchantmentPermanent("enchantments")));
    }

    private TempestOfLight(final TempestOfLight card) {
        super(card);
    }

    @Override
    public TempestOfLight copy() {
        return new TempestOfLight(this);
    }
}
