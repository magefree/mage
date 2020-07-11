

package mage.watchers.common;

import java.util.HashMap;
import java.util.Map;
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
public class NumberOfTimesPermanentTargetedATurnWatcher extends Watcher {

    private final Map<MageObjectReference, Integer> permanentsTargeted = new HashMap<>();

    public NumberOfTimesPermanentTargetedATurnWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.TARGETED) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null) {
                MageObjectReference mor = new MageObjectReference(permanent, game);
                int amount = 0;
                if (permanentsTargeted.containsKey(mor)) {
                    amount = permanentsTargeted.get(mor);
                }
                permanentsTargeted.put(mor, ++amount);
            }
        }
    }

    public boolean notMoreThanOnceTargetedThisTurn(Permanent creature, Game game) {
        if (permanentsTargeted.containsKey(new MageObjectReference(creature, game))) {
            return permanentsTargeted.get(new MageObjectReference(creature, game)) < 2;
        }
        return true;
    }

    @Override
    public void reset() {
        super.reset();
        permanentsTargeted.clear();
    }
}
