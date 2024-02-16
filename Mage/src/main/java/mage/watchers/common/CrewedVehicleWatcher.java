package mage.watchers.common;

import mage.MageObjectReference;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public class CrewedVehicleWatcher extends Watcher {

    private final Map<MageObjectReference, Set<MageObjectReference>> crewMap = new HashMap<>();

    public CrewedVehicleWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.CREWED_VEHICLE) {
            crewMap.computeIfAbsent(new MageObjectReference(event.getSourceId(), game), x -> new HashSet<>())
                    .add(new MageObjectReference(event.getTargetId(), game));
        }
    }

    @Override
    public void reset() {
        super.reset();
        crewMap.clear();
    }

    public static boolean checkIfCrewedThisTurn(Permanent crewer, Permanent crewed, Game game) {
        return game
                .getState()
                .getWatcher(CrewedVehicleWatcher.class)
                .crewMap
                .getOrDefault(new MageObjectReference(crewed, game), Collections.emptySet())
                .stream()
                .anyMatch(mor -> mor.refersTo(crewer, game));
    }
}
