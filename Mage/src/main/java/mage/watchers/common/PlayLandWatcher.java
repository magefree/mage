package mage.watchers.common;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

/**
 * @author jeffwadsworth
 */
public class PlayLandWatcher extends Watcher {

    final Set<UUID> playerPlayedLand = new HashSet<>(); // player that played land
    final Set<UUID> landPlayed = new HashSet<>(); // land played

    public PlayLandWatcher() {
        super(PlayLandWatcher.class.getSimpleName(), WatcherScope.GAME);
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
        playerPlayedLand.clear();
        landPlayed.clear();
        super.reset();
    }

    public boolean landPlayed(UUID playerId) {
        return playerPlayedLand.contains(playerId);
    }

    public boolean wasLandPlayed(UUID landId) {
        return landPlayed.contains(landId);
    }
}
