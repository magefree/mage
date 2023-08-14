
package mage.filter.predicate.permanent;

import mage.MageObjectReference;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;

/**
 * @author L_J
 */
public class AttackingSameNotBandedPredicate implements Predicate<Permanent> {

    private final MageObjectReference defenderMOR;

    public AttackingSameNotBandedPredicate(MageObjectReference defenderMOR) {
        this.defenderMOR = defenderMOR;
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        CombatGroup combatGroup = game.getCombat().findGroup(input.getId());
        return combatGroup != null
                && input.isAttacking()
                && input.getBandedCards().isEmpty()
                && combatGroup.getDefenderMOR() != null
                && combatGroup.getDefenderMOR().equals(defenderMOR);
    }
}
