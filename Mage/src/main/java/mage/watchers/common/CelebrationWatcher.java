package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Susucr
 */
public class CelebrationWatcher extends Watcher {

    // playerId -> number of nonland permanents entered the battlefield this turn under that player's control.
    private final Map<UUID, Integer> celebrationCounts = new HashMap<>();

    public CelebrationWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            return;
        }

        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null && !permanent.isLand(game)) {
            celebrationCounts.compute(permanent.getControllerId(), CardUtil::setOrIncrementValue);
        }
    }

    public boolean celebrationActive(UUID playerId) {
        return celebrationCounts.getOrDefault(playerId, 0) >= 2;
    }

    @Override
    public void reset() {
        super.reset();
        celebrationCounts.clear();
    }
}
