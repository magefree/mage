
package mage.filter.common;

import mage.constants.CardType;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author LevelX2
 */

public class FilterControlledPlaneswalkerPermanent extends FilterControlledPermanent {

    public FilterControlledPlaneswalkerPermanent() {
        this("planeswalker you control");
    }

    public FilterControlledPlaneswalkerPermanent(String name) {
        super(name);
        this.add(new CardTypePredicate(CardType.PLANESWALKER));
    }

    public FilterControlledPlaneswalkerPermanent(final FilterControlledPlaneswalkerPermanent filter) {
        super(filter);
    }

    @Override
    public FilterControlledPlaneswalkerPermanent copy() {
        return new FilterControlledPlaneswalkerPermanent(this);
    }

}
