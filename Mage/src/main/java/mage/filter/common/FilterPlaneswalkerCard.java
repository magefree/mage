

package mage.filter.common;

import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author fireshoes
 */
public class FilterPlaneswalkerCard extends FilterCard {

    public FilterPlaneswalkerCard() {
        this("planeswalker card");
    }

    public FilterPlaneswalkerCard(String name) {
        super(name);
        this.add(new CardTypePredicate(CardType.PLANESWALKER));
    }

    public FilterPlaneswalkerCard(final FilterPlaneswalkerCard filter) {
        super(filter);
    }

    @Override
    public FilterPlaneswalkerCard copy() {
        return new FilterPlaneswalkerCard(this);
    }

}
