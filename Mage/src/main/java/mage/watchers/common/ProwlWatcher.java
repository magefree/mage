
package mage.watchers.common;

import mage.abilities.keyword.ChangelingAbility;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.*;

/**
 * Watcher stores with which creature subtypes a player made combat damage to
 * other players during a turn.
 *
 * @author LevelX
 */
public class ProwlWatcher extends Watcher {

    private final Map<UUID, Set<SubType>> damagingSubtypes = new HashMap<>();
    private final Set<UUID> allSubtypes = new HashSet<>();

    public ProwlWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == EventType.DAMAGED_PLAYER) {
            DamagedPlayerEvent dEvent = (DamagedPlayerEvent) event;
            if (dEvent.isCombatDamage()) {
                Permanent creature = game.getPermanent(dEvent.getSourceId());
                if (creature != null && !allSubtypes.contains(creature.getControllerId())) {
                    if (creature.getAbilities().containsKey(ChangelingAbility.getInstance().getId()) || creature.isAllCreatureTypes()) {
                        allSubtypes.add(creature.getControllerId());
                    } else {
                        Set<SubType> subtypes = damagingSubtypes.getOrDefault(creature.getControllerId(), new LinkedHashSet<>());

                        subtypes.addAll(creature.getSubtype(game));
                        damagingSubtypes.put(creature.getControllerId(), subtypes);
                    }
                }
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        damagingSubtypes.clear();
        allSubtypes.clear();
    }

    public boolean hasSubtypeMadeCombatDamage(UUID playerId, SubType subtype) {
        if (allSubtypes.contains(playerId)) {
            return true;
        }
        Set<SubType> subtypes = damagingSubtypes.get(playerId);
        return subtypes != null && subtypes.contains(subtype);
    }

}
