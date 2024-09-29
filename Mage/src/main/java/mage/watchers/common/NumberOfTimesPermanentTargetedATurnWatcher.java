package mage.watchers.common;

import mage.MageObjectReference;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author LevelX2
 */
public class NumberOfTimesPermanentTargetedATurnWatcher extends Watcher {

    private final Map<MageObjectReference, Integer> permanentsTargeted = new HashMap<>();

    public NumberOfTimesPermanentTargetedATurnWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.TARGETED) {
            return;
        }
        StackObject targetingObject = CardUtil.getTargetingStackObject(event, game);
        if (targetingObject == null || CardUtil.checkTargetedEventAlreadyUsed(this.getKey(), targetingObject, event, game)) {
            return;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null) {
            MageObjectReference mor = new MageObjectReference(permanent, game);
            int nTimes = permanentsTargeted.getOrDefault(mor, 0);
            permanentsTargeted.put(mor, nTimes + 1);
        }
    }

    public int numTimesTargetedThisTurn(Permanent permanent, Game game) {
        return permanentsTargeted.getOrDefault(new MageObjectReference(permanent, game), 0);
    }

    @Override
    public void reset() {
        super.reset();
        permanentsTargeted.clear();
    }
}
