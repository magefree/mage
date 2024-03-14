package mage.filter.common;

import mage.MageObject;
import mage.abilities.keyword.SuspendAbility;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 * @author skiwkr
 */
public class FilterSuspendedCard extends FilterCard {

    protected FilterCard cardFilter;

    public FilterSuspendedCard() {
        this("suspended card");
    }

    public FilterSuspendedCard(String name) {
        super(name);
        cardFilter = new FilterCard();
        cardFilter.add(new AbilityPredicate(SuspendAbility.class));
        cardFilter.add(CounterType.TIME.getPredicate());
    }

    protected FilterSuspendedCard(final FilterSuspendedCard filter) {
        super(filter);
        this.cardFilter = filter.cardFilter.copy();
    }

    @Override
    public boolean checkObjectClass(Object object) {
        return object instanceof MageObject;
    }


    public FilterCard getCardFilter() {
        return this.cardFilter;
    }

    public void setCardFilter(FilterCard cardFilter) {
        this.cardFilter = cardFilter;
    }

    @Override
    public FilterSuspendedCard copy() {
        return new FilterSuspendedCard(this);
    }
}
