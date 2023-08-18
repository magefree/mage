

package mage.filter.common;

import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.FilterCard;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class FilterLandCard extends FilterCard {

    public FilterLandCard() {
        this("land card");
    }

    public FilterLandCard(String name) {
        super(name);
        this.add(CardType.LAND.getPredicate());
    }

    public static FilterLandCard basicLandCard() {
        FilterLandCard filter = new FilterLandCard("basic land card");
        filter.add(SuperType.BASIC.getPredicate());
        return filter;
    }

    protected FilterLandCard(final FilterLandCard filter) {
        super(filter);
    }

    @Override
    public FilterLandCard copy() {
        return new FilterLandCard(this);
    }

}
