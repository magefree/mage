
package mage.watchers.common;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;
import mage.players.PlayerList;

/**
 * @author spjspj
 */
public class PlayersAttackedLastTurnWatcher extends Watcher {

    // Store how many players each player attacked in their last turn 
    private final Map<UUID, PlayerList> playersAttackedInLastTurn = new HashMap<>();

    public PlayersAttackedLastTurnWatcher() {
        super(WatcherScope.GAME);
    }

    public PlayersAttackedLastTurnWatcher(final PlayersAttackedLastTurnWatcher watcher) {
        super(watcher);
        for (Map.Entry<UUID, PlayerList> entry : watcher.playersAttackedInLastTurn.entrySet()) {
            this.playersAttackedInLastTurn.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public PlayersAttackedLastTurnWatcher copy() {
        return new PlayersAttackedLastTurnWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BEGINNING_PHASE_PRE) {
            playersAttackedInLastTurn.remove(event.getPlayerId());
        }

        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED) {
            PlayerList playersAttacked = playersAttackedInLastTurn.get(event.getPlayerId());
            if (playersAttacked == null) {
                playersAttacked = new PlayerList();
            }
            UUID defender = game.getCombat().getDefendingPlayerId(event.getSourceId(), game);
            if (defender != null) {
                playersAttacked.add(defender);
            }
            playersAttackedInLastTurn.put(event.getPlayerId(), playersAttacked);
        }
    }

    public boolean attackedLastTurn(UUID playerId, UUID otherPlayerId) {
        if (playersAttackedInLastTurn.get(playerId) != null) {
            return playersAttackedInLastTurn.get(playerId).contains(otherPlayerId);
        }
        return false;
    }
}
