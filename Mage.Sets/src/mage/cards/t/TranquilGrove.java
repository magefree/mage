
package mage.cards.t;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author Quercitron
 */
public final class TranquilGrove extends CardImpl {

    private static final FilterPermanent filter = new FilterEnchantmentPermanent("other enchantments");
    
    static {
        filter.add(AnotherPredicate.instance);
    }
    
    public TranquilGrove(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{G}");


        // {1}{G}{G}: Destroy all other enchantments.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyAllEffect(filter), new ManaCostsImpl<>("{1}{G}{G}")));
    }

    private TranquilGrove(final TranquilGrove card) {
        super(card);
    }

    @Override
    public TranquilGrove copy() {
        return new TranquilGrove(this);
    }
}
