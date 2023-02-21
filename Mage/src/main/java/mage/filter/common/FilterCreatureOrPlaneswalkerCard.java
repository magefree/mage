

package mage.filter.common;

import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

public class FilterCreatureOrPlaneswalkerCard extends FilterCard {

    public FilterCreatureOrPlaneswalkerCard() {
        this("creature or planeswalker card");
    }

    public FilterCreatureOrPlaneswalkerCard(String name) {
        super(name);
        this.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()));
    }

    public FilterCreatureOrPlaneswalkerCard(final FilterCreatureOrPlaneswalkerCard filter) {
        super(filter);
    }

    @Override
    public FilterCreatureOrPlaneswalkerCard copy() {
        return new FilterCreatureOrPlaneswalkerCard(this);
    }
}
