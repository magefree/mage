package mage.filter.common;

import mage.constants.CardType;
import mage.filter.predicate.Predicates;

/**
 * Warning, it's a filter for damage effects only (ignore lands, artifacts and other non-damageable objects)
 *
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

    protected FilterAnyTarget(final FilterAnyTarget filter) {
        super(filter);
    }

    @Override
    public FilterAnyTarget copy() {
        return new FilterAnyTarget(this);
    }
}
