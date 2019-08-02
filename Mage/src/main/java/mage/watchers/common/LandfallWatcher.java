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
 * @author BetaSteward_at_googlemail.com
 * @author Loki
 */
public class LandfallWatcher extends Watcher {

    private final Set<UUID> playerPlayedLand = new HashSet<>(); // player that had a land enter the battlefield
    private final Set<UUID> landEnteredBattlefield = new HashSet<>(); // land played

    public LandfallWatcher() {
        super(WatcherScope.GAME);
    }

    private LandfallWatcher(final LandfallWatcher watcher) {
        super(watcher);
        playerPlayedLand.addAll(watcher.playerPlayedLand);
        landEnteredBattlefield.addAll(watcher.landEnteredBattlefield);
    }

    @Override
    public LandfallWatcher copy() {
        return new LandfallWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (permanent != null
                    && permanent.isLand()
                    && !playerPlayedLand.contains(event.getPlayerId())) {
                playerPlayedLand.add(event.getPlayerId());
                landEnteredBattlefield.add(event.getTargetId());
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerPlayedLand.clear();
        landEnteredBattlefield.clear();
    }

    public boolean landPlayed(UUID playerId) {
        return playerPlayedLand.contains(playerId);
    }

    public boolean landEnteredBattlefield(UUID landId) {
        return landEnteredBattlefield.contains(landId);
    }
}
