package mage.watchers.common;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

/**
 * @author TheElk801
 */

public class CardsDrawnThisTurnWatcher extends Watcher {

    private final Map<UUID, Integer> cardsDrawnThisTurn = new HashMap<>();

    public CardsDrawnThisTurnWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DREW_CARD) {
            int cardsDrawn = getCardsDrawnThisTurn(event.getPlayerId());
            cardsDrawnThisTurn.put(event.getPlayerId(), cardsDrawn + 1);
        }
    }

    public int getCardsDrawnThisTurn(UUID playerId) {
        return cardsDrawnThisTurn.getOrDefault(playerId, 0);
    }

    @Override
    public void reset() {
        super.reset();
        cardsDrawnThisTurn.clear();

    }
}
