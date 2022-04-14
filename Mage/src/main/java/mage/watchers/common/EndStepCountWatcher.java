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
public class EndStepCountWatcher extends Watcher {

    private final Map<UUID, Integer> playerMap = new HashMap<>();

    public EndStepCountWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.END_TURN_STEP_PRE) {
            playerMap.compute(game.getActivePlayerId(), CardUtil::setOrIncrementValue);
        }
    }

    public static int getCount(UUID playerId, Game game) {
        return game
                .getState()
                .getWatcher(EndStepCountWatcher.class)
                .playerMap
                .getOrDefault(playerId, 0);
    }
}
