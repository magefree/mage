
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
public final class Tranquility extends CardImpl {

    public Tranquility(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}");

        this.getSpellAbility().addEffect(new DestroyAllEffect(new FilterEnchantmentPermanent("enchantments")));
    }

    private Tranquility(final Tranquility card) {
        super(card);
    }

    @Override
    public Tranquility copy() {
        return new Tranquility(this);
    }
}
