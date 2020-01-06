

package mage.filter.common;

import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

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
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                CardType.LAND.getPredicate(),
                CardType.PLANESWALKER.getPredicate()));
    }

    public FilterPermanentCard(final FilterPermanentCard filter) {
        super(filter);
    }

    @Override
    public FilterPermanentCard copy() {
        return new FilterPermanentCard(this);
    }
}
