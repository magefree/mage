
package mage.filter.predicate.permanent;

import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author L_J
 */
public class AttackingSameNotBandedPredicate implements Predicate<Permanent> {

    private final UUID defenderId;

    public AttackingSameNotBandedPredicate(UUID defenderId) {
        this.defenderId = defenderId;
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        CombatGroup combatGroup = game.getCombat().findGroup(input.getId());
        return combatGroup != null
                    && input.isAttacking()
                    && input.getBandedCards().isEmpty()
                    && combatGroup.getDefenderId() != null
                    && combatGroup.getDefenderId().equals(defenderId);
    }
}
