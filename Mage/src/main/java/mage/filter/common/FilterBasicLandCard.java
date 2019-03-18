
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
public class FilterBasicLandCard extends FilterCard {

    public FilterBasicLandCard() {
        this("basic land card");
    }

    public FilterBasicLandCard(String name) {
        super(name);
        this.add(new CardTypePredicate(CardType.LAND));
        this.add(new SupertypePredicate(SuperType.BASIC));
    }

    public FilterBasicLandCard(final FilterBasicLandCard filter) {
        super(filter);
    }

    @Override
    public FilterBasicLandCard copy() {
        return new FilterBasicLandCard(this);
    }
}
