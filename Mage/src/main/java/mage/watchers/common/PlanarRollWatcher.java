
package mage.watchers.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

/*
 * Counts the number of times the planar die has been rolled per player per turn
 * This watcher is automatically started in gameImpl.init for each game
 *
 * @author spjspj
 */
public class PlanarRollWatcher extends Watcher {

    private final Map<UUID, Integer> numberTimesPlanarDieRolled = new HashMap<>();

    public PlanarRollWatcher() {
        super(PlanarRollWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public PlanarRollWatcher(final PlanarRollWatcher watcher) {
        super(watcher);
        for (Entry<UUID, Integer> entry : watcher.numberTimesPlanarDieRolled.entrySet()) {
            numberTimesPlanarDieRolled.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.PLANAR_DIE_ROLLED) {
            UUID playerId = event.getPlayerId();
            if (playerId != null) {
                Integer amount = numberTimesPlanarDieRolled.get(playerId);
                if (amount == null) {
                    amount = 1;
                } else {
                    amount ++;
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
        numberTimesPlanarDieRolled.clear();
    }

    @Override
    public PlanarRollWatcher copy() {
        return new PlanarRollWatcher(this);
    }
}
