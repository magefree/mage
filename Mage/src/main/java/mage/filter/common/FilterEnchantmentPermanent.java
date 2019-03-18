


package mage.filter.common;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author jeffwadsworth
 */
public class FilterEnchantmentPermanent extends FilterPermanent {

    public FilterEnchantmentPermanent() {
        this("enchantment");
    }

    public FilterEnchantmentPermanent(String name) {
        super(name);
        this.add(new CardTypePredicate(CardType.ENCHANTMENT));
    }

    public FilterEnchantmentPermanent(final FilterEnchantmentPermanent filter) {
        super(filter);
    }

    @Override
    public FilterEnchantmentPermanent copy() {
        return new FilterEnchantmentPermanent(this);
    }
}
