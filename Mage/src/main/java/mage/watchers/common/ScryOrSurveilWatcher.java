package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author muz
 */
public class ScryOrSurveilWatcher extends Watcher {

    private final Set<UUID> playerIds = new HashSet<>();

    public ScryOrSurveilWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SCRIED
                || event.getType() == GameEvent.EventType.SURVEILED) {
            playerIds.add(event.getPlayerId());
        }
    }

    public boolean hasScriedOrSurveilled(UUID playerId) {
        return playerIds.contains(playerId);
    }

    @Override
    public void reset() {
        super.reset();
        playerIds.clear();
    }
}