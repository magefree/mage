package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */

public class CardsDrawnThisTurnWatcher extends Watcher {

    private final Map<UUID, Integer> cardsDrawnThisTurn = new HashMap<>();

    public CardsDrawnThisTurnWatcher() {
        super(WatcherScope.GAME);
    }

    private CardsDrawnThisTurnWatcher(final CardsDrawnThisTurnWatcher watcher) {
        super(watcher);
        this.cardsDrawnThisTurn.putAll(watcher.cardsDrawnThisTurn);
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

    @Override
    public CardsDrawnThisTurnWatcher copy() {
        return new CardsDrawnThisTurnWatcher(this);
    }
}
