


package mage.filter.common;

import mage.constants.CardType;
import mage.filter.FilterPermanent;

/**
 * @author jeffwadsworth
 */
public class FilterEnchantmentPermanent extends FilterPermanent {

    public FilterEnchantmentPermanent() {
        this("enchantment");
    }

    public FilterEnchantmentPermanent(String name) {
        super(name);
        this.add(CardType.ENCHANTMENT.getPredicate());
    }

    protected FilterEnchantmentPermanent(final FilterEnchantmentPermanent filter) {
        super(filter);
    }

    @Override
    public FilterEnchantmentPermanent copy() {
        return new FilterEnchantmentPermanent(this);
    }
}
