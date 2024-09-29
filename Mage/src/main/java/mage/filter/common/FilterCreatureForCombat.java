

package mage.filter.common;

import mage.filter.predicate.permanent.TappedPredicate;

/**
 * @author BetaSteward_at_googlemail.com
 * @author North
 */
public class FilterCreatureForCombat extends FilterCreatureForCombatBase {

    public FilterCreatureForCombat() {
        this("");
    }

    public FilterCreatureForCombat(String name) {
        super(name);
        this.add(TappedPredicate.UNTAPPED);
    }

    protected FilterCreatureForCombat(final FilterCreatureForCombat filter) {
        super(filter);
    }

    @Override
    public FilterCreatureForCombat copy() {
        return new FilterCreatureForCombat(this);
    }
}
