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
public class DiscardedCardWatcher extends Watcher {

    private final Map<UUID, Integer> playerMap = new HashMap<>();

    public DiscardedCardWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DISCARDED_CARD) {
            playerMap.compute(event.getPlayerId(), CardUtil::setOrIncrementValue);
        }
    }

    @Override
    public void reset() {
        playerMap.clear();
        super.reset();
    }

    public static boolean checkPlayerDiscarded(UUID playerId, Game game) {
        DiscardedCardWatcher watcher = game.getState().getWatcher(DiscardedCardWatcher.class);
        return watcher != null && watcher.playerMap.getOrDefault(playerId, 0) > 0;
    }

    public static int getDiscarded(UUID playerId, Game game) {
        DiscardedCardWatcher watcher = game.getState().getWatcher(DiscardedCardWatcher.class);
        return watcher == null ? 0 : watcher.playerMap.getOrDefault(playerId, 0);
    }

    public static boolean playerInRangeDiscarded(UUID controllerId, Game game) {
        DiscardedCardWatcher watcher = game.getState().getWatcher(DiscardedCardWatcher.class);
        if (watcher != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controllerId, game)) {
                if (watcher.playerMap.getOrDefault(playerId, 0) > 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
