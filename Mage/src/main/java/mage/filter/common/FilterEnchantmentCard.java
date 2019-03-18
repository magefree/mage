
package mage.filter.common;

import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author LoneFox
 */
public class FilterEnchantmentCard extends FilterCard {

    public FilterEnchantmentCard() {
        this("enchantment card");
    }

    public FilterEnchantmentCard(String name) {
        super(name);
        this.add(new CardTypePredicate(CardType.ENCHANTMENT));
    }

    public FilterEnchantmentCard(final FilterEnchantmentCard filter) {
        super(filter);
    }

    @Override
    public FilterEnchantmentCard copy() {
        return new FilterEnchantmentCard(this);
    }
}
