package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public class CompletedDungeonWatcher extends Watcher {

    private final Map<UUID, Set<String>> playerMap = new HashMap<>();
    private static final Set<String> emptySet = new HashSet<>();

    public CompletedDungeonWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case DUNGEON_COMPLETED:
                playerMap.computeIfAbsent(event.getPlayerId(), u -> new HashSet<>()).add(event.getData());
                return;
            case BEGINNING_PHASE_PRE:
                if (game.getTurnNum() == 1) {
                    playerMap.clear();
                }
        }
    }

    public static boolean checkPlayer(UUID playerId, Game game) {
        CompletedDungeonWatcher watcher = game.getState().getWatcher(CompletedDungeonWatcher.class);
        return watcher != null && !watcher.playerMap.getOrDefault(playerId, emptySet).isEmpty();
    }

    public static Set<String> getCompletedNames(UUID playerId, Game game) {
        CompletedDungeonWatcher watcher = game.getState().getWatcher(CompletedDungeonWatcher.class);
        return watcher != null ? watcher.playerMap.getOrDefault(playerId, emptySet) : emptySet;
    }
}
