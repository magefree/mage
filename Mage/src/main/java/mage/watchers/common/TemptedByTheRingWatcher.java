package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class TemptedByTheRingWatcher extends Watcher {

    private final Map<UUID, Integer> map = new HashMap<>();

    public TemptedByTheRingWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case TEMPTED_BY_RING:
                map.compute(event.getPlayerId(), CardUtil::setOrIncrementValue);
                return;
            case BEGINNING_PHASE_PRE:
                if (game.getTurnNum() == 1) {
                    map.clear();
                }
        }
    }

    @Override
    public void reset() {
        super.reset();
    }

    public static int getCount(UUID playerId, Game game) {
        return game
                .getState()
                .getWatcher(TemptedByTheRingWatcher.class)
                .map
                .getOrDefault(playerId, 0);
    }
}
