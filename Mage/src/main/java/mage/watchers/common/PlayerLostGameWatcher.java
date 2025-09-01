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
public class PlayerLostGameWatcher extends Watcher {

    private final Set<UUID> players = new HashSet<>();

    public PlayerLostGameWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case BEGINNING_PHASE_PRE:
                if (game.getTurnNum() == 1) {
                    players.clear();
                }
                return;
            case LOST:
                players.add(event.getPlayerId());
        }
    }

    public static int getCount(Game game) {
        return game
                .getState()
                .getWatcher(PlayerLostGameWatcher.class)
                .players
                .size();
    }
}
