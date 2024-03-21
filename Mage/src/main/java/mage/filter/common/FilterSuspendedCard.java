package mage.filter.common;

import mage.abilities.keyword.SuspendAbility;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 * @author skiwkr
 */
public class FilterSuspendedCard extends FilterCard {

    public FilterSuspendedCard() {
        this("suspended card");
    }

    public FilterSuspendedCard(String name) {
        super(name);
        this.add(new AbilityPredicate(SuspendAbility.class));
        this.add(CounterType.TIME.getPredicate());
    }

    protected FilterSuspendedCard(final FilterSuspendedCard filter) {
        super(filter);
    }

    @Override
    public FilterSuspendedCard copy() {
        return new FilterSuspendedCard(this);
    }
}
