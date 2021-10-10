package mage.filter.predicate.permanent;

import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public enum BlockingOrBlockedBySourcePredicate implements ObjectSourcePlayerPredicate<Permanent> {
    BLOCKING,
    BLOCKED_BY,
    EITHER;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return game
                .getCombat()
                .getGroups()
                .stream()
                .anyMatch(combatGroup -> checkBlocks(combatGroup, input.getSourceId(), input.getObject().getId()));
    }

    private boolean checkBlocks(CombatGroup combatGroup, UUID thisCreature, UUID otherCreature) {
        switch (this) {
            case BLOCKING:
                return isBlocking(combatGroup, otherCreature, thisCreature);
            case BLOCKED_BY:
                return isBlocking(combatGroup, thisCreature, otherCreature);
            case EITHER:
                return isBlocking(combatGroup, otherCreature, thisCreature)
                        || isBlocking(combatGroup, thisCreature, otherCreature);
        }
        return false;
    }

    private static final boolean isBlocking(CombatGroup combatGroup, UUID id1, UUID id2) {
        return combatGroup.getBlockers().contains(id1) && combatGroup.getAttackers().contains(id2);
    }

    @Override
    public String toString() {
        return "Blocking or blocked by";
    }
}
