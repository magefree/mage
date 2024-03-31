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
public class SaddledMountWatcher extends Watcher {

    // key: the mount, value: set of creatures which saddled
    private final Map<MageObjectReference, Set<MageObjectReference>> saddleMap = new HashMap<>();

    public SaddledMountWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SADDLED_MOUNT) {
            saddleMap.computeIfAbsent(new MageObjectReference(event.getSourceId(), game), x -> new HashSet<>())
                    .add(new MageObjectReference(event.getTargetId(), game));
        }
    }

    @Override
    public void reset() {
        super.reset();
        saddleMap.clear();
    }

    public static boolean checkIfSaddledThisTurn(Permanent saddler, Permanent mount, Game game) {
        return game
                .getState()
                .getWatcher(SaddledMountWatcher.class)
                .saddleMap
                .getOrDefault(new MageObjectReference(mount, game), Collections.emptySet())
                .stream()
                .anyMatch(mor -> mor.refersTo(saddler, game));
    }

    public static int getSaddleCount(Permanent vehicle, Game game) {
        return game
                .getState()
                .getWatcher(SaddledMountWatcher.class)
                .saddleMap
                .getOrDefault(new MageObjectReference(vehicle, game), Collections.emptySet())
                .size();
    }
}
