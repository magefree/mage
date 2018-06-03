
package mage.watchers.common;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.watchers.Watcher;

/*
 *
 * @author Styxo
 */
public class LifeLossOtherFromCombatWatcher extends Watcher {

    private final Set<UUID> players = new HashSet<>();

    public LifeLossOtherFromCombatWatcher() {
        super(LifeLossOtherFromCombatWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public LifeLossOtherFromCombatWatcher(final LifeLossOtherFromCombatWatcher watcher) {
        super(watcher);
        this.players.addAll(watcher.players);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.LOST_LIFE && !event.getFlag()) {
            UUID playerId = event.getPlayerId();
            if (playerId != null) {
                players.add(playerId);
            }
        }
    }

    public boolean opponentLostLifeOtherFromCombat(UUID playerId, Game game) {
        Player player = game.getPlayer(playerId);
        if (player != null) {
            if (players.stream().anyMatch(damagedPlayerId -> player.hasOpponent(damagedPlayerId, game))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void reset() {
        super.reset();
        players.clear();
    }

    @Override
    public LifeLossOtherFromCombatWatcher copy() {
        return new LifeLossOtherFromCombatWatcher(this);
    }
}
