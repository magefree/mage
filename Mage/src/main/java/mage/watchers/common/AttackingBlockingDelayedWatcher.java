package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Stores attacking/blocking combat information but is only updated
 *  1) as damage is dealt (combat damage or otherwise)
 *  2) as a spell or ability starts resolving
 *  3) Land playing or special action taken (just in case that then causes a static effect to kill)
 * Thus the information is available after any involved creatures die
 * and all dying creatures can see all other creatures that were in combat at that time
 * WARNING: This information is NOT to be used for static effects since the information will always be outdated.
 * Use game.getCombat() directly or one of the other combat watchers instead
 * @author notgreat
 */
public class AttackingBlockingDelayedWatcher extends Watcher {

    private Set<UUID> attackers = new HashSet<>();
    private Set<UUID> blockers = new HashSet<>();

    public AttackingBlockingDelayedWatcher() {
        super(WatcherScope.GAME);
    }
    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case LAND_PLAYED:
            case TAKEN_SPECIAL_ACTION:
            case RESOLVING_ABILITY:
            case DAMAGED_BATCH_FOR_PERMANENTS:
                //Note: getAttackers and getBlockers make a new Set, so this is safe to do
                attackers = game.getCombat().getAttackers();
                blockers = game.getCombat().getBlockers();
        }
    }

    @Override
    public void reset() {
        super.reset();
        attackers.clear();
        blockers.clear();
    }

    public boolean checkAttacker(UUID attacker) {
        return attackers.contains(attacker);
    }

    public boolean checkBlocker(UUID blocker) {
        return blockers.contains(blocker);
    }

    public long countBlockers() {
        return blockers.size();
    }

    public long countAttackers() {
        return attackers.size();
    }

    public static AttackingBlockingDelayedWatcher getWatcher(Game game) {
        return game.getState().getWatcher(AttackingBlockingDelayedWatcher.class);
    }
}
