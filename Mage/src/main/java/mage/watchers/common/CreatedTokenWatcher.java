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
public class CreatedTokenWatcher extends Watcher {

    private final Map<UUID, Integer> playerMap = new HashMap<>();

    public CreatedTokenWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.CREATED_TOKEN) {
            playerMap.compute(event.getPlayerId(), CardUtil::setOrIncrementValue);
        }
    }

    @Override
    public void reset() {
        playerMap.clear();
    }

    public static boolean checkPlayer(UUID playerId, Game game) {
        return getPlayerCount(playerId, game) > 0;
    }

    public static int getPlayerCount(UUID playerId, Game game) {
        return game
                .getState()
                .getWatcher(CreatedTokenWatcher.class)
                .playerMap
                .getOrDefault(playerId, 0);
    }
}
