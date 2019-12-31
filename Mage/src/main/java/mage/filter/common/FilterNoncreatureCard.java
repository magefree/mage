package mage.filter.common;

import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 * @author ssouders412
 */
public class FilterNoncreatureCard extends FilterCard {

    public FilterNoncreatureCard() {
        this("noncreature card");
    }

    public FilterNoncreatureCard(String name) {
        super(name);
        this.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
    }

    public FilterNoncreatureCard(final FilterNoncreatureCard filter) {
        super(filter);
    }

    @Override
    public FilterNoncreatureCard copy() {
        return new FilterNoncreatureCard(this);
    }
}
