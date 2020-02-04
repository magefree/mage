
package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Quercitron
 * @author LevelX2
 * @author jeffwadsworth
 */
public class CardsAmountDrawnThisTurnWatcher extends Watcher {


    private final Map<UUID, Integer> amountOfCardsDrawnThisTurn = new HashMap<>();

    public CardsAmountDrawnThisTurnWatcher() {
        super(WatcherScope.GAME);
    }


    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DREW_CARD) {
            amountOfCardsDrawnThisTurn.putIfAbsent(event.getPlayerId(), 0);
            amountOfCardsDrawnThisTurn.compute(event.getPlayerId(), (k, amount) -> amount + 1);
        }
    }

    @Override
    public void reset() {
        super.reset();
        amountOfCardsDrawnThisTurn.clear();
    }

    public boolean opponentDrewXOrMoreCards(UUID playerId, int x, Game game) {
        Player player = game.getPlayer(playerId);
        if (player != null) {
            for (Map.Entry<UUID, Integer> entry : amountOfCardsDrawnThisTurn.entrySet()) {
                if (game.isOpponent(player, entry.getKey())) {
                    if (entry.getValue() >= x) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int getAmountCardsDrawn(UUID playerId) {
        return amountOfCardsDrawnThisTurn.getOrDefault(playerId, 0);
    }
}
