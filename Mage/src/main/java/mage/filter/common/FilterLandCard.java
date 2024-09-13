package mage.filter.common;

import mage.constants.CardType;
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

    protected FilterLandCard(final FilterLandCard filter) {
        super(filter);
    }

    @Override
    public FilterLandCard copy() {
        return new FilterLandCard(this);
    }

}
