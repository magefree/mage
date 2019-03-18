

package mage.filter.common;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class FilterLandPermanent extends FilterPermanent {

    public FilterLandPermanent() {
        this("land");
    }

    public FilterLandPermanent(String name) {
        super(name);
        this.add(new CardTypePredicate(CardType.LAND));
    }

    public FilterLandPermanent(SubType subtype, String name) {
        super(name);
        this.add(new CardTypePredicate(CardType.LAND));
        this.add(new SubtypePredicate(subtype));
    }

    public static FilterLandPermanent nonbasicLand() {
        FilterLandPermanent filter = new FilterLandPermanent("nonbasic land");
        filter.add(Predicates.not(new SupertypePredicate(SuperType.BASIC)));
        return filter;
    }

    public static FilterLandPermanent nonbasicLands() {
        FilterLandPermanent filter = new FilterLandPermanent("nonbasic lands");
        filter.add(Predicates.not(new SupertypePredicate(SuperType.BASIC)));
        return filter;
    }

    public FilterLandPermanent(final FilterLandPermanent filter) {
        super(filter);
    }

    @Override
    public FilterLandPermanent copy() {
        return new FilterLandPermanent(this);
    }
}
