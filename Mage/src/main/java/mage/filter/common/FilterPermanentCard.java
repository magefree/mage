

package mage.filter.common;

import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author Plopman
 */
public class FilterPermanentCard extends FilterCard {

    public FilterPermanentCard() {
        this("permanent card");
    }

    public FilterPermanentCard(String name) {
        super(name);
        this.add(
                Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.ENCHANTMENT),
                new CardTypePredicate(CardType.LAND),
                new CardTypePredicate(CardType.PLANESWALKER)));
    }

    public FilterPermanentCard(final FilterPermanentCard filter) {
        super(filter);
    }

    @Override
    public FilterPermanentCard copy() {
        return new FilterPermanentCard(this);
    }
}
