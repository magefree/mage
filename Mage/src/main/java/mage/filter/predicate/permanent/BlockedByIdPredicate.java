package mage.filter.predicate.permanent;

import java.util.UUID;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.watchers.common.BlockedAttackerWatcher;

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
        } // Check if the blockerId was blocked before, if it does no longer exists now but so the target attacking is still valid
        Permanent blocker = game.getPermanentOrLKIBattlefield(blockerId);
        if (blocker != null) {
            BlockedAttackerWatcher watcher = game.getState().getWatcher(BlockedAttackerWatcher.class);
            if (watcher != null) {
                return watcher.creatureHasBlockedAttacker(input, blocker, game);
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Blocked by " + blockerId.toString();
    }
}
