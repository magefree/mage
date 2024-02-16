package mage.watchers.common;

import java.util.*;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public class PermanentsSacrificedWatcher extends Watcher {

    private final Map<UUID, List<Permanent>> sacrificedPermanents = new HashMap<>();

    public PermanentsSacrificedWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT) {
            Permanent perm = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (perm != null) {
                List<Permanent> permanents;
                if (!sacrificedPermanents.containsKey(perm.getControllerId())) {
                    permanents = new ArrayList<>();
                    sacrificedPermanents.put(perm.getControllerId(), permanents);
                } else {
                    permanents = sacrificedPermanents.get(perm.getControllerId());
                }
                permanents.add(perm);
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        sacrificedPermanents.clear();
    }

    public List<Permanent> getThisTurnSacrificedPermanents(UUID playerId) {
        return sacrificedPermanents.get(playerId);
    }

    public int getThisTurnSacrificedPermanents() {
        return sacrificedPermanents.values().stream().mapToInt(permanents -> permanents.size()).sum();
    }
}
