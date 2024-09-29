package mage.watchers.common;

import mage.cards.Card;
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
 * @author TheElk801
 */
public class DescendedWatcher extends Watcher {

    private final Map<UUID, Integer> playerMap = new HashMap<>();

    public DescendedWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ZONE_CHANGE) {
            return;
        }
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        Card card = game.getCard(zEvent.getTargetId());
        if (card != null && card.isPermanent(game) && zEvent.getToZone() == Zone.GRAVEYARD) {
            playerMap.compute(card.getOwnerId(), CardUtil::setOrIncrementValue);
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerMap.clear();
    }

    public static int getDescendedCount(UUID playerId, Game game) {
        return game
                .getState()
                .getWatcher(DescendedWatcher.class)
                .playerMap
                .getOrDefault(playerId, 0);
    }
}
