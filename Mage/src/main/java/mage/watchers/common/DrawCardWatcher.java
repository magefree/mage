package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public class DrawCardWatcher extends Watcher {

    private final Map<UUID, List<UUID>> drawMap = new HashMap<>();

    DrawCardWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.DREW_CARD) {
            return;
        }
        if (!drawMap.containsKey(event.getPlayerId())) {
            drawMap.putIfAbsent(event.getPlayerId(), new ArrayList<>());
        }
        drawMap.get(event.getPlayerId()).add(event.getId());
    }

    @Override
    public void reset() {
        super.reset();
        drawMap.clear();
    }

    public static boolean checkEvent(UUID playerId, GameEvent event, Game game, int cardNumber) {
        Map<UUID, List<UUID>> drawMap = game.getState().getWatcher(DrawCardWatcher.class).drawMap;
        return drawMap.containsKey(playerId) && Objects.equals(drawMap.get(playerId).size(), cardNumber) && event.getId().equals(drawMap.get(playerId).get(cardNumber - 1));
    }
}
