package mage.filter.common;

import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class FilterBasicCard extends FilterCard {

    public FilterBasicCard(SubType subType) {
        this(subType, "basic " + subType + " card");
    }

    public FilterBasicCard(String name) {
        this(null, name);
    }

    public FilterBasicCard(SubType subType, String name) {
        super(subType, name);
        this.add(SuperType.BASIC.getPredicate());
    }

    protected FilterBasicCard(final FilterBasicCard filter) {
        super(filter);
    }

    @Override
    public FilterBasicCard copy() {
        return new FilterBasicCard(this);
    }
}
