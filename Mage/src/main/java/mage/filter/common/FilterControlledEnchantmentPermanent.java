
package mage.filter.common;

import mage.constants.CardType;

/**
 * @author LevelX2
 */

public class FilterControlledEnchantmentPermanent extends FilterControlledPermanent {

    public FilterControlledEnchantmentPermanent() {
        this("enchantment you control");
    }

    /**
     * @param name
     */
    public FilterControlledEnchantmentPermanent(String name) {
        super(name);
        this.add(CardType.ENCHANTMENT.getPredicate());
    }

    protected FilterControlledEnchantmentPermanent(final FilterControlledEnchantmentPermanent filter) {
        super(filter);
    }

    @Override
    public FilterControlledEnchantmentPermanent copy() {
        return new FilterControlledEnchantmentPermanent(this);
    }

}