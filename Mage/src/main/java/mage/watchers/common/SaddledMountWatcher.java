package mage.watchers.common;

import mage.MageObjectReference;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801, Susucr
 */
public class SaddledMountWatcher extends Watcher {

    // key: the mount mor, value: set of creatures which saddled (on Saddle Cost payment)
    private final Map<MageObjectReference, Set<MageObjectReference>> saddleMap = new HashMap<>();

    // set of mount mor actually saddled (on Saddle Ability resolution)
    private final Set<MageObjectReference> saddledSet = new HashSet<>();

    public SaddledMountWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case SADDLED_MOUNT:
                saddleMap.computeIfAbsent(new MageObjectReference(event.getSourceId(), game), x -> new HashSet<>())
                        .add(new MageObjectReference(event.getTargetId(), game));
                break;
            case MOUNT_SADDLED:
                saddledSet.add(new MageObjectReference(event.getSourceId(), game));
                break;
        }
    }

    @Override
    public void reset() {
        super.reset();
        saddleMap.clear();
        saddledSet.clear();
    }

    public static boolean hasBeenSaddledThisTurn(MageObjectReference mountMOR, Game game) {
        SaddledMountWatcher watcher = game.getState().getWatcher(SaddledMountWatcher.class);
        return watcher != null && watcher.saddledSet.contains(mountMOR);
    }

    public static boolean checkIfSaddledThisTurn(Permanent saddler, MageObjectReference mountMOR, Game game) {
        return hasBeenSaddledThisTurn(mountMOR, game) && game
                .getState()
                .getWatcher(SaddledMountWatcher.class)
                .saddleMap
                .getOrDefault(mountMOR, Collections.emptySet())
                .stream()
                .anyMatch(mor -> mor.refersTo(saddler, game));
    }
}
