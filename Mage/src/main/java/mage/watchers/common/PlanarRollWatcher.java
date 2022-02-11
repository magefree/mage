package mage.watchers.common;

import mage.constants.RollDieType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.DieRolledEvent;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/*
 * Counts the number of times the planar die has been rolled per player per turn
 * This watcher is automatically started in gameImpl.init for each game
 *
 * @author spjspj
 */
public class PlanarRollWatcher extends Watcher {

    private final Map<UUID, Integer> numberTimesPlanarDieRolled = new HashMap<>();

    public PlanarRollWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DIE_ROLLED) {
            DieRolledEvent drEvent = (DieRolledEvent) event;
            UUID playerId = drEvent.getPlayerId();
            if (playerId != null && drEvent.getRollDieType() == RollDieType.PLANAR) {
                Integer amount = numberTimesPlanarDieRolled.get(playerId);
                if (amount == null) {
                    amount = 1;
                } else {
                    amount++;
                }
                numberTimesPlanarDieRolled.put(playerId, amount);
            }
        }
    }

    public int getNumberTimesPlanarDieRolled(UUID playerId) {
        return numberTimesPlanarDieRolled.getOrDefault(playerId, 0);
    }

    @Override
    public void reset() {
        super.reset();
        numberTimesPlanarDieRolled.clear();
    }

}
