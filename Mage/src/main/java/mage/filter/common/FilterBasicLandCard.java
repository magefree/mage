
package mage.filter.common;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class FilterBasicLandCard extends FilterCard {

    public FilterBasicLandCard() {
        this("basic land card");
    }

    public FilterBasicLandCard(SubType subType) {
        this("basic " + subType + " card");
        this.add(subType.getPredicate());
    }

    public FilterBasicLandCard(String name) {
        super(name);
        this.add(CardType.LAND.getPredicate());
        this.add(SuperType.BASIC.getPredicate());
    }

    public FilterBasicLandCard(final FilterBasicLandCard filter) {
        super(filter);
    }

    @Override
    public FilterBasicLandCard copy() {
        return new FilterBasicLandCard(this);
    }
}
