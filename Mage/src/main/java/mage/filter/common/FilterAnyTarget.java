package mage.filter.common;

import mage.constants.CardType;
import mage.filter.predicate.Predicates;

/**
 * @author TheElk801
 */
public class FilterAnyTarget extends FilterPermanentOrPlayer {

    public FilterAnyTarget() {
        this("any target");
    }

    public FilterAnyTarget(String name) {
        super(name);
        this.permanentFilter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate(),
                CardType.BATTLE.getPredicate()
        ));
    }

    public FilterAnyTarget(final FilterAnyTarget filter) {
        super(filter);
    }

    @Override
    public FilterAnyTarget copy() {
        return new FilterAnyTarget(this);
    }
}
