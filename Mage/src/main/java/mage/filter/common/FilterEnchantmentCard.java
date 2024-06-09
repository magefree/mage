
package mage.filter.common;

import mage.constants.CardType;
import mage.filter.FilterCard;

/**
 * @author LoneFox
 */
public class FilterEnchantmentCard extends FilterCard {

    public FilterEnchantmentCard() {
        this("enchantment card");
    }

    public FilterEnchantmentCard(String name) {
        super(name);
        this.add(CardType.ENCHANTMENT.getPredicate());
    }

    protected FilterEnchantmentCard(final FilterEnchantmentCard filter) {
        super(filter);
    }

    @Override
    public FilterEnchantmentCard copy() {
        return new FilterEnchantmentCard(this);
    }
}
