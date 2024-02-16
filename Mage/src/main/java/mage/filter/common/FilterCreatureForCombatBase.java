

package mage.filter.common;

import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;


/**
 * @author BetaSteward_at_googlemail.com
 * @author North
 */
public class FilterCreatureForCombatBase extends FilterCreaturePermanent {

    public FilterCreatureForCombatBase() {
        this("");
    }

    public FilterCreatureForCombatBase(String name) {
        super(name);
        this.add(Predicates.not(AttackingPredicate.instance));
        this.add(new PhasedInPredicate());
        this.add(new CanBlockPredicate());
    }

    protected FilterCreatureForCombatBase(final FilterCreatureForCombatBase filter) {
        super(filter);
    }

    @Override
    public FilterCreatureForCombatBase copy() {
        return new FilterCreatureForCombatBase(this);
    }
}

class PhasedInPredicate implements Predicate<Permanent> {

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.isPhasedIn();
    }

    @Override
    public String toString() {
        return "PhasedIn";
    }
}

class CanBlockPredicate implements Predicate<Permanent> {

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.getMaxBlocks() == 0 || input.getBlocking() < input.getMaxBlocks();
    }

    @Override
    public String toString() {
        return "CanBlock";
    }
}
