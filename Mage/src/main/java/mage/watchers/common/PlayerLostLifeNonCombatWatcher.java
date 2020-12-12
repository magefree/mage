package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/*
 * Counts amount of life lost from noncombat sources current or last turn by players.
 * This watcher is automatically started in gameImpl.init for each game
 *
 * @author NinthWorld
 */
public class PlayerLostLifeNonCombatWatcher extends Watcher {

    private final Map<UUID, Integer> amountOfLifeLostThisTurn = new HashMap<>();
    private final Map<UUID, Integer> amountOfLifeLostLastTurn = new HashMap<>();

    public PlayerLostLifeNonCombatWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        // non combat lose life
        if (event.getType() == GameEvent.EventType.LOST_LIFE && !event.getFlag()) {
            UUID playerId = event.getPlayerId();
            if (playerId != null) {
                Integer amount = amountOfLifeLostThisTurn.get(playerId);
                if (amount == null) {
                    amount = event.getAmount();
                } else {
                    amount = amount + event.getAmount();
                }
                amountOfLifeLostThisTurn.put(playerId, amount);
            }
        }
    }

    public int getLiveLost(UUID playerId) {
        return amountOfLifeLostThisTurn.getOrDefault(playerId, 0);
    }

    public int getAllOppLifeLost(UUID playerId, Game game) {
        int amount = 0;
        for (UUID opponentId : this.amountOfLifeLostThisTurn.keySet()) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null && opponent.hasOpponent(playerId, game)) {
                amount += this.amountOfLifeLostThisTurn.getOrDefault(opponentId, 0);
            }
        }
        return amount;
    }

    public int getLiveLostLastTurn(UUID playerId) {
        return amountOfLifeLostLastTurn.getOrDefault(playerId, 0);
    }

    @Override
    public void reset() {
        super.reset();
        amountOfLifeLostLastTurn.clear();
        amountOfLifeLostLastTurn.putAll(amountOfLifeLostThisTurn);
        amountOfLifeLostThisTurn.clear();
    }
}
