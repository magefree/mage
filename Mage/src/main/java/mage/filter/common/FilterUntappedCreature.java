

package mage.filter.common;

import mage.filter.predicate.permanent.TappedPredicate;

/**
 *
 * @author noxx
 */
public class FilterUntappedCreature extends FilterCreaturePermanent {

    public FilterUntappedCreature() {
        this("untapped creature");
    }

    public FilterUntappedCreature(String name) {
        super(name);
        this.add(TappedPredicate.UNTAPPED);
    }

    public FilterUntappedCreature(final FilterUntappedCreature filter) {
        super(filter);
    }

    @Override
    public FilterUntappedCreature copy() {
        return new FilterUntappedCreature(this);
    }
}
