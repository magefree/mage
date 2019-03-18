
package mage.filter.predicate.permanent;

import java.util.UUID;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class BlockingAttackerIdPredicate implements Predicate<Permanent> {

    private final UUID attackerId;

    public BlockingAttackerIdPredicate(UUID attackerId) {
        this.attackerId = attackerId;
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        for (CombatGroup combatGroup : game.getCombat().getGroups()) {
            if (combatGroup.getBlockers().contains(input.getId()) && combatGroup.getAttackers().contains(attackerId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Blocking attacker with id " + attackerId.toString();
    }
}