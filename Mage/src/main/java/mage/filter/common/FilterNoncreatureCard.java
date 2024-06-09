package mage.filter.common;

import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

/**
 * @author ssouders412
 */
public class FilterNoncreatureCard extends FilterCard {

    public FilterNoncreatureCard() {
        this("noncreature card");
    }

    public FilterNoncreatureCard(String name) {
        super(name);
        this.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    protected FilterNoncreatureCard(final FilterNoncreatureCard filter) {
        super(filter);
    }

    @Override
    public FilterNoncreatureCard copy() {
        return new FilterNoncreatureCard(this);
    }
}
