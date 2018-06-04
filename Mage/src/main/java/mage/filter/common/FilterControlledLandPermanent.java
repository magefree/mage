

package mage.filter.common;

import mage.constants.CardType;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author LevelX2
 */
public class FilterControlledLandPermanent extends FilterControlledPermanent {

    public FilterControlledLandPermanent() {
        this("land you control");
    }

    public FilterControlledLandPermanent(String name) {
        super(name);
        this.add(new CardTypePredicate(CardType.LAND));
    }

    public FilterControlledLandPermanent(final FilterControlledLandPermanent filter) {
        super(filter);
    }

    @Override
    public FilterControlledLandPermanent copy() {
        return new FilterControlledLandPermanent(this);
    }

}
