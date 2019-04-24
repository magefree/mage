
package mage.watchers.common;

import java.util.HashMap;
import java.util.UUID;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.watchers.Watcher;

/**
 * @author LevelX2
 */
public class CreaturesDiedWatcher extends Watcher {

    private final HashMap<UUID, Integer> amountOfCreaturesThatDiedByController = new HashMap<>();
    private final HashMap<UUID, Integer> amountOfCreaturesThatDiedByOwner = new HashMap<>();

    public CreaturesDiedWatcher() {
        super(CreaturesDiedWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public CreaturesDiedWatcher(final CreaturesDiedWatcher watcher) {
        super(watcher);
        this.amountOfCreaturesThatDiedByController.putAll(watcher.amountOfCreaturesThatDiedByController);
        this.amountOfCreaturesThatDiedByOwner.putAll(watcher.amountOfCreaturesThatDiedByOwner);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.isDiesEvent()
                    && zEvent.getTarget() != null
                    && zEvent.getTarget().isCreature()) {
                int amount = getAmountOfCreaturesDiedThisTurnByController(zEvent.getTarget().getControllerId());
                amountOfCreaturesThatDiedByController.put(zEvent.getTarget().getControllerId(), amount + 1);
                amount = getAmountOfCreaturesDiedThisTurnByOwner(zEvent.getTarget().getOwnerId());
                amountOfCreaturesThatDiedByOwner.put(zEvent.getTarget().getOwnerId(), amount + 1);
            }
        }
    }

    @Override
    public void reset() {
        amountOfCreaturesThatDiedByController.clear();
        amountOfCreaturesThatDiedByOwner.clear();
    }

    public int getAmountOfCreaturesDiedThisTurnByController(UUID playerId) {
        return amountOfCreaturesThatDiedByController.getOrDefault(playerId, 0);
    }

    public int getAmountOfCreaturesDiedThisTurnByOwner(UUID playerId) {
        return amountOfCreaturesThatDiedByOwner.getOrDefault(playerId, 0);
    }

    @Override
    public CreaturesDiedWatcher copy() {
        return new CreaturesDiedWatcher(this);
    }

    public int getAmountOfCreaturesDiedThisTurn() {
        return amountOfCreaturesThatDiedByController.values().stream().mapToInt(x -> x).sum();
    }
}
