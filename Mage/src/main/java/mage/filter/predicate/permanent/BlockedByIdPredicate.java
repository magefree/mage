
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
public class BlockedByIdPredicate implements Predicate<Permanent> {

    private final UUID blockerId;

    public BlockedByIdPredicate(UUID blockerId) {
        this.blockerId = blockerId;
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        for (CombatGroup combatGroup : game.getCombat().getGroups()) {
            if (combatGroup.getBlockers().contains(blockerId) && combatGroup.getAttackers().contains(input.getId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Blocked by " + blockerId.toString();
    }
}
