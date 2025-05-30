package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Susucr
 */
public class LifeLostThisTurnWatcher extends Watcher {

    // player -> number of times (not amount!) that player lost life this turn.
    private final Map<UUID, Integer> playersLostLife = new HashMap<>();

    public LifeLostThisTurnWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.LOST_LIFE_BATCH_FOR_ONE_PLAYER) {
            playersLostLife.compute(event.getTargetId(), CardUtil::setOrIncrementValue);
        }
    }


    @Override
    public void reset() {
        super.reset();
        playersLostLife.clear();
    }

    public int timesLostLifeThisTurn(UUID playerId) {
        return playersLostLife.getOrDefault(playerId, 0);
    }
}
