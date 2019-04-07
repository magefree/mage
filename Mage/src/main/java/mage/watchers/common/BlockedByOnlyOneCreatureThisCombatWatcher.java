
package mage.watchers.common;

import java.util.*;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

/**
 *
 * @author L_J
 */
public class BlockedByOnlyOneCreatureThisCombatWatcher extends Watcher {

    private final Map<CombatGroup, UUID> blockedByOneCreature = new HashMap<>();

    public BlockedByOnlyOneCreatureThisCombatWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BEGIN_COMBAT_STEP_PRE) {
            this.blockedByOneCreature.clear();
        }
        else if (event.getType() == GameEvent.EventType.BLOCKER_DECLARED) {
            CombatGroup combatGroup = game.getCombat().findGroup(event.getTargetId());
            if (combatGroup != null) {
                if (combatGroup.getBlockers().size() == 1) {
                    if (!blockedByOneCreature.containsKey(combatGroup)) {
                        blockedByOneCreature.put(combatGroup, event.getSourceId());
                    }
                    else if (!Objects.equals(blockedByOneCreature.get(combatGroup), event.getSourceId())) {
                        blockedByOneCreature.put(combatGroup, null);
                    }
                }
                else if (combatGroup.getBlockers().size() > 1) {
                    blockedByOneCreature.put(combatGroup, null);
                }
            }
        }
    }

    public Set<CombatGroup> getBlockedOnlyByCreature(UUID creature) {
        Set<CombatGroup> combatGroups = new HashSet<>();
        for (Map.Entry<CombatGroup, UUID> entry : blockedByOneCreature.entrySet()) {
            if (entry.getValue() != null) {
                if (entry.getValue().equals(creature)) {
                    combatGroups.add(entry.getKey());
                }
            }
        }
        if (combatGroups.size() > 0) {
            return combatGroups;
        }
        return null;
    }

}
