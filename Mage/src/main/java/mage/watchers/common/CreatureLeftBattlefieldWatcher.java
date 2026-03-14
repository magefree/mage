package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Susucr
 */
public class CreatureLeftBattlefieldWatcher extends Watcher {

    // player -> number of creatures that left the battlefield under that player's control this turn
    private final Map<UUID, Integer> mapCreaturesLeft = new HashMap<>();

    public CreatureLeftBattlefieldWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ZONE_CHANGE) {
            return;
        }
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (Zone.BATTLEFIELD.match(zEvent.getFromZone()) && zEvent.getTarget().isCreature(game)) {
            mapCreaturesLeft.compute(zEvent.getTarget().getControllerId(), CardUtil::setOrIncrementValue);
        }
    }

    @Override
    public void reset() {
        super.reset();
        mapCreaturesLeft.clear();
    }

    public static int getNumberCreatureLeft(UUID playerId, Game game) {
        return game
                .getState()
                .getWatcher(CreatureLeftBattlefieldWatcher.class)
                .mapCreaturesLeft
                .getOrDefault(playerId, 0);
    }
}
