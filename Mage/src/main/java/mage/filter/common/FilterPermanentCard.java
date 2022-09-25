package mage.filter.common;

import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.PermanentPredicate;

/**
 * @author Plopman
 */
public class FilterPermanentCard extends FilterCard {

    public FilterPermanentCard() {
        this("permanent card");
    }

    public FilterPermanentCard(String name) {
        super(name);
        this.add(PermanentPredicate.instance);
    }

    public FilterPermanentCard(final FilterPermanentCard filter) {
        super(filter);
    }

    @Override
    public FilterPermanentCard copy() {
        return new FilterPermanentCard(this);
    }
}
