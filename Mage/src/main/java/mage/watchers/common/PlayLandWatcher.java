package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public class PlayLandWatcher extends Watcher {

    private final Set<UUID> playerPlayedLand = new HashSet<>(); // player that played land
    private final Set<UUID> landPlayed = new HashSet<>(); // land played

    public PlayLandWatcher() {
        super(WatcherScope.GAME);
    }

    public PlayLandWatcher(final PlayLandWatcher watcher) {
        super(watcher);
        playerPlayedLand.addAll(watcher.playerPlayedLand);
        landPlayed.addAll(watcher.landPlayed);
    }

    @Override
    public PlayLandWatcher copy() {
        return new PlayLandWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.LAND_PLAYED) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (permanent != null
                    && permanent.isLand()
                    && !playerPlayedLand.contains(event.getPlayerId())) {
                playerPlayedLand.add(event.getPlayerId());
                landPlayed.add(event.getTargetId());
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerPlayedLand.clear();
        landPlayed.clear();
    }

    public boolean landPlayed(UUID playerId) {
        return playerPlayedLand.contains(playerId);
    }

    public boolean wasLandPlayed(UUID landId) {
        return landPlayed.contains(landId);
    }
}
