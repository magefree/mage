package mage.watchers.common;

import mage.MageObjectReference;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author notgreat
 */
public class DamagedPlayerThisCombatWatcher extends Watcher {
    // Watch over creatures that dealt combat damage to a player the last damage phase of current combat.
    // Gets cleared post combat damage step.

    // Player ID -> List of permanents they controlled that dealt damage
    private final Map<UUID, List<MageObjectReference>> permanents = new HashMap<>();
    // MOR -> Player they dealt damage to
    private final Map<MageObjectReference, UUID> damageTarget = new HashMap<>();

    public DamagedPlayerThisCombatWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_POST
                || event.getType() == GameEvent.EventType.CLEANUP_STEP_POST) {
            permanents.clear();
            damageTarget.clear();
            return;
        }
        if (event.getType() != GameEvent.EventType.DAMAGED_PLAYER
                || !((DamagedPlayerEvent) event).isCombatDamage()) {
            return;
        }
        Permanent creature = game.getPermanent(event.getSourceId());
        if (creature == null) {
            return;
        }
        MageObjectReference mor = new MageObjectReference(creature, game);
        damageTarget.put(mor, event.getPlayerId());

        List<MageObjectReference> list = permanents.computeIfAbsent(creature.getControllerId(), (key) -> new ArrayList<>());
        list.add(mor);
    }

    // Return the set of permanents that the controller controlled which dealt combat damage to the player,
    // during the very last combat step. (so no first striker on normal combat damage)
    // Returns empty set if there were none
    public Set<MageObjectReference> getPermanents(UUID controllerID, UUID damagedPlayerID) {
        return permanents.getOrDefault(controllerID, Collections.emptyList()).stream()
                         .filter((mor) -> damagedPlayerID.equals(damageTarget.get(mor)))
                         .collect(Collectors.toSet());
    }
}
