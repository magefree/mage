

package mage.filter.common;

import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;

/**
 * @author LevelX2
 */
public class FilterCreatureOrPlaneswalkerPermanent extends FilterPermanent {

    public FilterCreatureOrPlaneswalkerPermanent() {
        this("creature or planeswalker");
    }

    public FilterCreatureOrPlaneswalkerPermanent(String name) {
        super(name);
        this.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()));
    }

    protected FilterCreatureOrPlaneswalkerPermanent(final FilterCreatureOrPlaneswalkerPermanent filter) {
        super(filter);
    }

    @Override
    public FilterCreatureOrPlaneswalkerPermanent copy() {
        return new FilterCreatureOrPlaneswalkerPermanent(this);
    }
}
