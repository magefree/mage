package mage.filter.common;

import mage.MageObject;
import mage.abilities.keyword.SuspendAbility;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.MultiFilterImpl;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 * @author emerald000
 */
public class FilterPermanentOrSuspendedCard extends MultiFilterImpl<MageObject> {

    public FilterPermanentOrSuspendedCard() {
        this("permanent or suspended card");
    }

    public FilterPermanentOrSuspendedCard(String name) {
        super(name, new FilterPermanent(), new FilterCard());
        this.getCardFilter().add(new AbilityPredicate(SuspendAbility.class));
        this.getCardFilter().add(CounterType.TIME.getPredicate());
    }

    protected FilterPermanentOrSuspendedCard(final FilterPermanentOrSuspendedCard filter) {
        super(filter);
    }

    @Override
    public FilterPermanentOrSuspendedCard copy() {
        return new FilterPermanentOrSuspendedCard(this);
    }

    public FilterPermanent getPermanentFilter() {
        return (FilterPermanent) this.innerFilters.get(0);
    }

    public FilterCard getCardFilter() {
        return (FilterCard) this.innerFilters.get(1);
    }
}
