package mage.filter.predicate.permanent;

import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author anishtilekar
 */
public enum AttackingAlonePredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.isAttacking()
                && game.getCombat().attacksAlone()
                && game.getCombat().getAttackers().contains(input.getId());
    }

    @Override
    public String toString() {
        return "Attacking alone";
    }
}
