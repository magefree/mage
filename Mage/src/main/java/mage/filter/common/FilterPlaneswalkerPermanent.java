

package mage.filter.common;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class FilterPlaneswalkerPermanent extends FilterPermanent {

    public FilterPlaneswalkerPermanent() {
        this("planeswalker");
    }

    public FilterPlaneswalkerPermanent(SubType subType) {
        this(subType.getDescription() + " planeswalker");
        this.add(new SubtypePredicate(subType));
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
