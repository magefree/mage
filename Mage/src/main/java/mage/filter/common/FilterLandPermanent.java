

package mage.filter.common;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class FilterLandPermanent extends FilterPermanent {

    public FilterLandPermanent() {
        this("land");
    }

    public FilterLandPermanent(String name) {
        super(name);
        this.add(CardType.LAND.getPredicate());
    }

    public FilterLandPermanent(SubType subtype, String name) {
        super(name);
        this.add(CardType.LAND.getPredicate());
        this.add(subtype.getPredicate());
    }

    public static FilterLandPermanent nonbasicLand() {
        FilterLandPermanent filter = new FilterLandPermanent("nonbasic land");
        filter.add(Predicates.not(SuperType.BASIC.getPredicate()));
        return filter;
    }

    public static FilterLandPermanent nonbasicLands() {
        FilterLandPermanent filter = new FilterLandPermanent("nonbasic lands");
        filter.add(Predicates.not(SuperType.BASIC.getPredicate()));
        return filter;
    }

    protected FilterLandPermanent(final FilterLandPermanent filter) {
        super(filter);
    }

    @Override
    public FilterLandPermanent copy() {
        return new FilterLandPermanent(this);
    }
}
