
package mage.filter.common;

import mage.filter.predicate.permanent.BlockingPredicate;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class FilterBlockingCreature extends FilterCreaturePermanent {

    public FilterBlockingCreature() {
        this("blocking creature");
    }

    public FilterBlockingCreature(String name) {
        super(name);
        this.add(BlockingPredicate.instance);
    }

    protected FilterBlockingCreature(final FilterBlockingCreature filter) {
        super(filter);
    }

    @Override
    public FilterBlockingCreature copy() {
        return new FilterBlockingCreature(this);
    }
}
