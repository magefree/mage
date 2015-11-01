package mage.watchers.common;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

/**
 * @author BetaSteward_at_googlemail.com
 * @author Loki
 */
public class LandfallWatcher extends Watcher {

    Set<UUID> playerPlayedLand = new HashSet<>();

    public LandfallWatcher() {
        super("LandPlayed", WatcherScope.GAME);
    }

    public LandfallWatcher(final LandfallWatcher watcher) {
        super(watcher);
        playerPlayedLand.addAll(watcher.playerPlayedLand);
    }

    @Override
    public LandfallWatcher copy() {
        return new LandfallWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (permanent != null && permanent.getCardType().contains(CardType.LAND) && !playerPlayedLand.contains(event.getPlayerId())) {
                playerPlayedLand.add(event.getPlayerId());
            }
        }
    }

    @Override
    public void reset() {
        playerPlayedLand.clear();
        super.reset();
    }

    public boolean landPlayed(UUID playerId) {
        return playerPlayedLand.contains(playerId);
    }
}
