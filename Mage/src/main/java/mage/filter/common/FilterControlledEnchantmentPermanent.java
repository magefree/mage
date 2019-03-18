
package mage.filter.common;

import mage.constants.CardType;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author LevelX2
 */

public class FilterControlledEnchantmentPermanent extends FilterControlledPermanent {

    public FilterControlledEnchantmentPermanent() {
        this("enchantment you control");
    }

    /**
     *
     * @param name
     */
    public FilterControlledEnchantmentPermanent(String name) {
        super(name);
        this.add(new CardTypePredicate(CardType.ENCHANTMENT));
    }

    public FilterControlledEnchantmentPermanent(final FilterControlledEnchantmentPermanent filter) {
        super(filter);
    }

    @Override
    public FilterControlledEnchantmentPermanent copy() {
        return new FilterControlledEnchantmentPermanent(this);
    }

}