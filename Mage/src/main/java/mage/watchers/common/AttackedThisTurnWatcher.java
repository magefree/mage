package mage.watchers.common;

import mage.MageObjectReference;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author magenoxx_at_gmail.com
 */
public class AttackedThisTurnWatcher extends Watcher {

    private final Set<MageObjectReference> attackedThisTurnCreatures = new HashSet<>();
    private final Map<MageObjectReference, Integer> attackedThisTurnCreaturesCounts = new HashMap<>();

    public AttackedThisTurnWatcher() {
        super(WatcherScope.GAME);
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

    public int getAttackCount(Permanent permanent, Game game) {
        return this.attackedThisTurnCreaturesCounts.getOrDefault(new MageObjectReference(permanent, game), 0);
    }

    public boolean checkIfAttacked(Permanent permanent, Game game) {
        for (MageObjectReference mor : attackedThisTurnCreatures) {
            if (mor.refersTo(permanent, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void reset() {
        super.reset();
        this.attackedThisTurnCreatures.clear();
        this.attackedThisTurnCreaturesCounts.clear();
    }
}
