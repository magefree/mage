package mage.filter.common;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.predicate.Predicates;

/**
 * @author LevelX2
 */
public class FilterControlledCreatureOrPlaneswalkerPermanent extends FilterControlledPermanent {

    public FilterControlledCreatureOrPlaneswalkerPermanent() {
        this("creature or planeswalker you control");
    }

    public FilterControlledCreatureOrPlaneswalkerPermanent(SubType subType) {
        this(subType, "a " + subType + " creature or a " + subType + " planeswalker");
    }

    public FilterControlledCreatureOrPlaneswalkerPermanent(SubType subType, String name) {
        super(name);
        this.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
        this.add(subType.getPredicate());
    }

    public FilterControlledCreatureOrPlaneswalkerPermanent(String name) {
        super(name);
        this.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    public FilterControlledCreatureOrPlaneswalkerPermanent(final FilterControlledCreatureOrPlaneswalkerPermanent filter) {
        super(filter);
    }

    @Override
    public FilterControlledCreatureOrPlaneswalkerPermanent copy() {
        return new FilterControlledCreatureOrPlaneswalkerPermanent(this);
    }

}
