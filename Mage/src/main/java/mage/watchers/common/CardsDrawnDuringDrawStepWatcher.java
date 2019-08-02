package mage.watchers.common;

import mage.constants.PhaseStep;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

/**
 * @author LevelX2
 * <p>
 * Counts cards drawn during draw step
 */

public class CardsDrawnDuringDrawStepWatcher extends Watcher {

    private final Map<UUID, Integer> amountOfCardsDrawnThisTurn = new HashMap<>();

    public CardsDrawnDuringDrawStepWatcher() {
        super(WatcherScope.GAME);
    }

    public CardsDrawnDuringDrawStepWatcher(final CardsDrawnDuringDrawStepWatcher watcher) {
        super(watcher);
        for (Entry<UUID, Integer> entry : watcher.amountOfCardsDrawnThisTurn.entrySet()) {
            amountOfCardsDrawnThisTurn.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DREW_CARD
                && game.getPhase() != null
                && game.getPhase().getStep().getType() == PhaseStep.DRAW) {
            UUID playerId = event.getPlayerId();
            if (playerId != null) {
                amountOfCardsDrawnThisTurn.putIfAbsent(playerId, 0);
                amountOfCardsDrawnThisTurn.compute(playerId, (k, amount) -> amount + 1);

            }
        }
    }

    public int getAmountCardsDrawn(UUID playerId) {
        return amountOfCardsDrawnThisTurn.getOrDefault(playerId, 0);
    }

    @Override
    public void reset() {
        super.reset();
        amountOfCardsDrawnThisTurn.clear();
    }

    @Override
    public CardsDrawnDuringDrawStepWatcher copy() {
        return new CardsDrawnDuringDrawStepWatcher(this);
    }
}
