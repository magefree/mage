package mage.filter.common;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 * @author LevelX2
 */
public class FilterControlledPlaneswalkerPermanent extends FilterControlledPermanent {

    public FilterControlledPlaneswalkerPermanent() {
        this("planeswalker you control");
    }

    public FilterControlledPlaneswalkerPermanent(SubType subType) {
        this(subType, "a " + subType + " planeswalker");
    }

    public FilterControlledPlaneswalkerPermanent(SubType subType, String name) {
        super(name);
        this.add(new CardTypePredicate(CardType.PLANESWALKER));
        this.add(new SubtypePredicate(subType));
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
