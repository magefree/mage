

package mage.filter.common;

import mage.filter.predicate.permanent.AttackingPredicate;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class FilterAttackingCreature extends FilterCreaturePermanent {

    public FilterAttackingCreature() {
        this("attacking creature");
    }

    public FilterAttackingCreature(String name) {
        super(name);
        this.add(AttackingPredicate.instance);
    }

    protected FilterAttackingCreature(final FilterAttackingCreature filter) {
        super(filter);
    }

    @Override
    public FilterAttackingCreature copy() {
        return new FilterAttackingCreature(this);
    }
}
