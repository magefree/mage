package mage.watchers.common;

import java.util.*;
import java.util.stream.Collectors;

import mage.MageObjectReference;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public class BlockedAttackerWatcher extends Watcher {

    // key: blocking creatures
    // value: set of creatures blocked
    private final Map<MageObjectReference, Set<MageObjectReference>> blockerMap = new HashMap<>();

    /**
     * Game default watcher
     */
    public BlockedAttackerWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BLOCKER_DECLARED) {
            blockerMap.computeIfAbsent(new MageObjectReference(event.getSourceId(), game), k -> new HashSet<>())
                    .add(new MageObjectReference(event.getTargetId(), game));
        }
    }

    @Override
    public void reset() {
        super.reset();
        blockerMap.clear();
    }

    public boolean creatureHasBlockedAttacker(Permanent attacker, Permanent blocker, Game game) {
        return blockerMap.getOrDefault(new MageObjectReference(blocker, game), Collections.emptySet())
                .contains(new MageObjectReference(attacker, game));
    }

    public boolean creatureHasBlockedAttacker(MageObjectReference attacker, MageObjectReference blocker) {
        return blockerMap.getOrDefault(blocker, Collections.emptySet()).contains(attacker);
    }

    public Set<Permanent> getBlockedCreatures(MageObjectReference blocker, Game game) {
        return blockerMap.getOrDefault(blocker, Collections.emptySet())
                .stream()
                .map(m -> m.getPermanent(game))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }
}
