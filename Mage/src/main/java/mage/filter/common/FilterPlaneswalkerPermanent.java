

package mage.filter.common;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class FilterPlaneswalkerPermanent extends FilterPermanent {

    public FilterPlaneswalkerPermanent() {
        this("planeswalker");
    }

    public FilterPlaneswalkerPermanent(SubType subType) {
        this(subType.getDescription() + " planeswalker");
        this.add(subType.getPredicate());
    }

    public FilterPlaneswalkerPermanent(String name) {
        super(name);
        this.add(CardType.PLANESWALKER.getPredicate());
    }

    protected FilterPlaneswalkerPermanent(final FilterPlaneswalkerPermanent filter) {
        super(filter);
    }

    @Override
    public FilterPlaneswalkerPermanent copy() {
        return new FilterPlaneswalkerPermanent(this);
    }
}
