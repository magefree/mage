package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author LevelX2
 */
public class CreaturesDiedWatcher extends Watcher {

    private final Map<UUID, Integer> amountOfCreaturesThatDiedByController = new HashMap<>();
    private final Map<UUID, Integer> amountOfCreaturesThatDiedByOwner = new HashMap<>();

    public CreaturesDiedWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ZONE_CHANGE) {
            return;
        }
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (!zEvent.isDiesEvent()
                || zEvent.getTarget() == null
                || !zEvent.getTarget().isCreature(game)) {
            return;
        }
        amountOfCreaturesThatDiedByController.compute(zEvent.getTarget().getControllerId(), CardUtil::setOrIncrementValue);
        amountOfCreaturesThatDiedByOwner.compute(zEvent.getTarget().getOwnerId(), CardUtil::setOrIncrementValue);
    }

    @Override
    public void reset() {
        super.reset();
        amountOfCreaturesThatDiedByController.clear();
        amountOfCreaturesThatDiedByOwner.clear();
    }

    public int getAmountOfCreaturesDiedThisTurnByController(UUID playerId) {
        return amountOfCreaturesThatDiedByController.getOrDefault(playerId, 0);
    }

    public int getAmountOfCreaturesDiedThisTurnByOwner(UUID playerId) {
        return amountOfCreaturesThatDiedByOwner.getOrDefault(playerId, 0);
    }

    public int getAmountOfCreaturesDiedThisTurn() {
        return amountOfCreaturesThatDiedByController.values().stream().mapToInt(x -> x).sum();
    }
}
