

package mage.filter.common;

import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author LevelX2
 */
public class FilterCreatureOrPlaneswalkerPermanent extends FilterPermanent {

    public FilterCreatureOrPlaneswalkerPermanent() {
        this("creature or planeswalker");
    }

    public FilterCreatureOrPlaneswalkerPermanent(String name) {
        super(name);
        this.add(Predicates.or(
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.PLANESWALKER)));
    }

    public FilterCreatureOrPlaneswalkerPermanent(final FilterCreatureOrPlaneswalkerPermanent filter) {
        super(filter);
    }

    @Override
    public FilterCreatureOrPlaneswalkerPermanent copy() {
        return new FilterCreatureOrPlaneswalkerPermanent(this);
    }
}
