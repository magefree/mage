package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Cguy7777
 */
public class ArtifactEnteredControllerWatcher extends Watcher {

    private final Set<UUID> players = new HashSet<>();

    public ArtifactEnteredControllerWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            return;
        }

        EntersTheBattlefieldEvent eEvent = (EntersTheBattlefieldEvent) event;
        if (eEvent.getTarget() != null && eEvent.getTarget().isArtifact(game)) {
            players.add(eEvent.getTarget().getControllerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        players.clear();
    }

    public static boolean enteredArtifactForPlayer(UUID playerId, Game game) {
        return game
                .getState()
                .getWatcher(ArtifactEnteredControllerWatcher.class)
                .players
                .contains(playerId);
    }
}
