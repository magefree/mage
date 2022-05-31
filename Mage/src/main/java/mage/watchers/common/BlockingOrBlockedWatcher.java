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
public class BlockingOrBlockedWatcher extends Watcher {

    private final Map<MageObjectReference, Set<MageObjectReference>> blockerMap = new HashMap<>();

    public BlockingOrBlockedWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case BLOCKER_DECLARED:
                blockerMap
                        .computeIfAbsent(new MageObjectReference(event.getTargetId(), game), x -> new HashSet<>())
                        .add(new MageObjectReference(event.getSourceId(), game));
                return;
            case END_COMBAT_STEP_POST:
                blockerMap.clear();
                return;
            case REMOVED_FROM_COMBAT:
                blockerMap
                        .values()
                        .stream()
                        .forEach(set -> set.removeIf(mor -> mor.refersTo(event.getTargetId(), game)));
        }
    }

    @Override
    public void reset() {
        super.reset();
        blockerMap.clear();
    }

    public static boolean check(Permanent attacker, Permanent blocker, Game game) {
        return game.getState()
                .getWatcher(BlockingOrBlockedWatcher.class)
                .blockerMap
                .getOrDefault(new MageObjectReference(attacker, game), Collections.emptySet())
                .stream()
                .anyMatch(mor -> mor.refersTo(blocker, game));
    }

    public static boolean check(Permanent blocker, Game game) {
        return game.getState()
                .getWatcher(BlockingOrBlockedWatcher.class)
                .blockerMap
                .values()
                .stream()
                .flatMap(Collection::stream)
                .anyMatch(mor -> mor.refersTo(blocker, game));
    }
}
