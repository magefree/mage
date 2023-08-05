

package mage.filter.common;

import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.BlockingPredicate;

/**
 * @author nantuko
 */
public class FilterAttackingOrBlockingCreature extends FilterCreaturePermanent {

    public FilterAttackingOrBlockingCreature() {
        this("attacking or blocking creature");
    }

    public FilterAttackingOrBlockingCreature(String name) {
        super(name);
        this.add(Predicates.or(
                AttackingPredicate.instance,
                BlockingPredicate.instance));
    }

    protected FilterAttackingOrBlockingCreature(final FilterAttackingOrBlockingCreature filter) {
        super(filter);
    }

    @Override
    public FilterAttackingOrBlockingCreature copy() {
        return new FilterAttackingOrBlockingCreature(this);
    }
}
