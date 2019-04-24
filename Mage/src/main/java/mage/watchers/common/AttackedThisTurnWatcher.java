
package mage.watchers.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import mage.MageObjectReference;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

/**
 * @author magenoxx_at_gmail.com
 */
public class AttackedThisTurnWatcher extends Watcher {

    protected final Set<MageObjectReference> attackedThisTurnCreatures = new HashSet<>();
    protected final Map<MageObjectReference, Integer> attackedThisTurnCreaturesCounts = new HashMap<>();

    public AttackedThisTurnWatcher() {
        super(AttackedThisTurnWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public AttackedThisTurnWatcher(final AttackedThisTurnWatcher watcher) {
        super(watcher);
        this.attackedThisTurnCreatures.addAll(watcher.attackedThisTurnCreatures);
        this.attackedThisTurnCreaturesCounts.putAll(watcher.attackedThisTurnCreaturesCounts);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED) {
            MageObjectReference mor = new MageObjectReference(event.getSourceId(), game);
            this.attackedThisTurnCreatures.add(mor);
            this.attackedThisTurnCreaturesCounts.putIfAbsent(mor, 0);
            this.attackedThisTurnCreaturesCounts.compute(mor, (k, v) -> (v + 1));
        }
    }

    public Set<MageObjectReference> getAttackedThisTurnCreatures() {
        return this.attackedThisTurnCreatures;
    }

    public Map<MageObjectReference, Integer> getAttackedThisTurnCreaturesCounts() {
        return this.attackedThisTurnCreaturesCounts;
    }

    @Override
    public AttackedThisTurnWatcher copy() {
        return new AttackedThisTurnWatcher(this);
    }

    @Override
    public void reset() {
        super.reset();
        this.attackedThisTurnCreatures.clear();
        this.attackedThisTurnCreaturesCounts.clear();
    }

}
