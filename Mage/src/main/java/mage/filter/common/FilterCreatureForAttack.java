

package mage.filter.common;

import mage.abilities.keyword.DefenderAbility;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.BlockingPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author BetaSteward_at_googlemail.com
 * @author North
 */
public class FilterCreatureForAttack extends FilterCreaturePermanent {

    public FilterCreatureForAttack() {
        this("");
    }

    public FilterCreatureForAttack(String name) {
        super(name);
        this.add(Predicates.not(AttackingPredicate.instance));
        this.add(Predicates.not(BlockingPredicate.instance));
        this.add(TappedPredicate.UNTAPPED);
        this.add(Predicates.not(new AbilityPredicate(DefenderAbility.class)));
        this.add(new CanTapPredicate());
    }

    protected FilterCreatureForAttack(final FilterCreatureForAttack filter) {
        super(filter);
    }

    @Override
    public FilterCreatureForAttack copy() {
        return new FilterCreatureForAttack(this);
    }
}

class CanTapPredicate implements Predicate<Permanent> {

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.canTap(game);
    }

    @Override
    public String toString() {
        return "CanTap";
    }
}
