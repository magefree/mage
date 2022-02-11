package mage.watchers.common;

import mage.MageObjectReference;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;

/**
 * @author LevelX2
 */
public class AttackedOrBlockedThisCombatWatcher extends Watcher {

    private final Set<MageObjectReference> attackedThisTurnCreatures = new HashSet<>();
    private final Set<MageObjectReference> blockedThisTurnCreatures = new HashSet<>();

    public AttackedOrBlockedThisCombatWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case BEGIN_COMBAT_STEP_PRE:
                this.attackedThisTurnCreatures.clear();
                this.blockedThisTurnCreatures.clear();
                return;
            case ATTACKER_DECLARED:
                this.attackedThisTurnCreatures.add(new MageObjectReference(event.getSourceId(), game));
                return;
            case BLOCKER_DECLARED:
                this.blockedThisTurnCreatures.add(new MageObjectReference(event.getSourceId(), game));
        }
    }

    public Set<MageObjectReference> getAttackedThisTurnCreatures() {
        return this.attackedThisTurnCreatures;
    }

    public Set<MageObjectReference> getBlockedThisTurnCreatures() {
        return this.blockedThisTurnCreatures;
    }

}
