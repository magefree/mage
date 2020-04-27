package mage.watchers.common;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class AbilityResolvedWatcher extends Watcher {

    private final Map<MageObjectReference, Map<UUID, Integer>> activationMap = new HashMap<>();

    public AbilityResolvedWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
    }

    @Override
    public void reset() {
        super.reset();
        activationMap.clear();
    }

    public boolean checkActivations(Ability source, Game game) {
        return activationMap.computeIfAbsent(new MageObjectReference(
                source.getSourceId(), source.getSourceObjectZoneChangeCounter(), game
        ), x -> new HashMap<>()).compute(source.getOriginalId(), (u, i) -> i == null ? 1 : i + 1).intValue() == 3;
    }
}
