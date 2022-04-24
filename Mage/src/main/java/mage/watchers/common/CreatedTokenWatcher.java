package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class CreatedTokenWatcher extends Watcher {

    private final Set<UUID> playerIds = new HashSet<>();

    public CreatedTokenWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.CREATED_TOKEN) {
            playerIds.add(event.getPlayerId());
        }
    }

    @Override
    public void reset() {
        playerIds.clear();
    }

    public static boolean checkPlayer(UUID playerId, Game game) {
        return game.getState().getWatcher(CreatedTokenWatcher.class).playerIds.contains(playerId);
    }
}
