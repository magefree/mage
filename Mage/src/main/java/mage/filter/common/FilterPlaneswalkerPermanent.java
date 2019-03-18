

package mage.filter.common;

import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class FilterPlaneswalkerPermanent extends FilterPermanent {

    public FilterPlaneswalkerPermanent() {
        this("planeswalker");
    }

    public FilterPlaneswalkerPermanent(String name) {
        super(name);
        this.add(new CardTypePredicate(CardType.PLANESWALKER));
    }

    public FilterPlaneswalkerPermanent(final FilterPlaneswalkerPermanent filter) {
        super(filter);
    }

    @Override
    public FilterPlaneswalkerPermanent copy() {
        return new FilterPlaneswalkerPermanent(this);
    }
}
