package mage.watchers.common;

import mage.cards.Card;
import mage.constants.SubType;
import mage.constants.SubTypeSet;
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
        if (event.getType() != EventType.DAMAGED_PLAYER) {
            return;
        }
        DamagedPlayerEvent dEvent = (DamagedPlayerEvent) event;
        if (!dEvent.isCombatDamage()) {
            return;
        }
        Permanent creature = game.getPermanent(dEvent.getSourceId());
        if (creature == null || allSubtypes.contains(creature.getControllerId())) {
            return;
        }
        if (creature.isAllCreatureTypes(game)) {
            allSubtypes.add(creature.getControllerId());
            return;
        }
        damagingSubtypes
                .computeIfAbsent(creature.getControllerId(), m -> new LinkedHashSet<>())
                .addAll(creature.getSubtype(game));
    }

    @Override
    public void reset() {
        super.reset();
        damagingSubtypes.clear();
        allSubtypes.clear();
    }

    public boolean hasSubtypeMadeCombatDamage(UUID playerId, Card card, Game game) {
        if (allSubtypes.contains(playerId)) {
            return true;
        }
        Set<SubType> subtypes = damagingSubtypes.get(playerId);
        return subtypes != null
                && subtypes
                .stream()
                .filter(subType -> subType.getSubTypeSet() == SubTypeSet.CreatureType)
                .anyMatch(subType -> card.hasSubtype(subType, game));
    }

}
