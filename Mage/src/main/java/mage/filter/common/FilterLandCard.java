

package mage.filter.common;

import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class FilterLandCard extends FilterCard {

    public FilterLandCard() {
        this("land card");
    }

    public FilterLandCard(String name) {
        super(name);
        this.add(new CardTypePredicate(CardType.LAND));
    }

    public static FilterLandCard basicLandCard() {
        FilterLandCard filter = new FilterLandCard("basic land card");
        filter.add(new SupertypePredicate(SuperType.BASIC));
        return filter;
    }

    public FilterLandCard(final FilterLandCard filter) {
        super(filter);
    }

    @Override
    public FilterLandCard copy() {
        return new FilterLandCard(this);
    }

}
