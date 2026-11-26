package mage.watchers.common;

import mage.MageObjectReference;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class NumberOfTimesPermanentTargetedATurnWatcher extends Watcher {

    private final Map<MageObjectReference, Integer> permanentsTargeted = new HashMap<>();
    // The same counts split by the controller of the targeting spell or ability.
    private final Map<MageObjectReference, Map<UUID, Integer>> permanentsTargetedByController = new HashMap<>();

    public NumberOfTimesPermanentTargetedATurnWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.TARGETED) {
            return;
        }
        StackObject targetingObject = game.findTargetingStackObject(this.getKey(), event);
        if (targetingObject == null) {
            return;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null) {
            MageObjectReference mor = new MageObjectReference(permanent, game);
            int nTimes = permanentsTargeted.getOrDefault(mor, 0);
            permanentsTargeted.put(mor, nTimes + 1);
            permanentsTargetedByController
                    .computeIfAbsent(mor, k -> new HashMap<>())
                    .merge(targetingObject.getControllerId(), 1, Integer::sum);
        }
    }

    public int numTimesTargetedThisTurn(Permanent permanent, Game game) {
        return permanentsTargeted.getOrDefault(new MageObjectReference(permanent, game), 0);
    }

    public int numTimesTargetedThisTurnByController(Permanent permanent, UUID controllerId, Game game) {
        Map<UUID, Integer> byController = permanentsTargetedByController.get(new MageObjectReference(permanent, game));
        return byController == null ? 0 : byController.getOrDefault(controllerId, 0);
    }

    @Override
    public void reset() {
        super.reset();
        permanentsTargeted.clear();
        permanentsTargetedByController.clear();
    }
}
