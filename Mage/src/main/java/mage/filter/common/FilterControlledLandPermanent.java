package mage.filter.common;

import mage.constants.CardType;
import mage.constants.SubType;

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
        this.add(CardType.LAND.getPredicate());
    }

    public FilterControlledLandPermanent(SubType subtype, String name) {
        super(name);
        this.add(CardType.LAND.getPredicate());
        this.add(subtype.getPredicate());
    }

    public FilterControlledLandPermanent(final FilterControlledLandPermanent filter) {
        super(filter);
    }

    @Override
    public FilterControlledLandPermanent copy() {
        return new FilterControlledLandPermanent(this);
    }
}
