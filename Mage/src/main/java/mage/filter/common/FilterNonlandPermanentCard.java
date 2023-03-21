

package mage.filter.common;

import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

public class FilterNonlandPermanentCard extends FilterCard {

    public FilterNonlandPermanentCard() {
        this("nonland permanent card");
    }

    public FilterNonlandPermanentCard(String name) {
        super(name);
        this.add(Predicates.not(CardType.LAND.getPredicate()));
        this.add(Predicates.not(CardType.INSTANT.getPredicate()));
        this.add(Predicates.not(CardType.SORCERY.getPredicate()));
    }

    public FilterNonlandPermanentCard(final FilterNonlandPermanentCard filter) {
        super(filter);
    }

    @Override
    public FilterNonlandPermanentCard copy() {
        return new FilterNonlandPermanentCard(this);
    }
}
