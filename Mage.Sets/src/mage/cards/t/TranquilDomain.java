
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author LoneFox
 */
public final class TranquilDomain extends CardImpl {

    private static final FilterEnchantmentPermanent filter = new FilterEnchantmentPermanent("non-Aura enchantments");

    static {
        filter.add(Predicates.not(new SubtypePredicate(SubType.AURA)));
    }

    public TranquilDomain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        // Destroy all non-Aura enchantments.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
    }

    public TranquilDomain(final TranquilDomain card) {
        super(card);
    }

    @Override
    public TranquilDomain copy() {
        return new TranquilDomain(this);
    }
}
