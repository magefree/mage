package mage.watchers.common;

import java.util.HashSet;
import java.util.Set;
import mage.MageObjectReference;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

/**
 *
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
        if (event.getType() == GameEvent.EventType.BEGIN_COMBAT_STEP_PRE) {
            this.getAttackedThisTurnCreatures().clear();
        }
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED) {
            this.getAttackedThisTurnCreatures().add(new MageObjectReference(event.getSourceId(), game));
        }
        if (event.getType() == GameEvent.EventType.BLOCKER_DECLARED) {
            this.getBlockedThisTurnCreatures().add(new MageObjectReference(event.getSourceId(), game));
        }
    }

    public Set<MageObjectReference> getAttackedThisTurnCreatures() {
        return this.attackedThisTurnCreatures;
    }

    public Set<MageObjectReference> getBlockedThisTurnCreatures() {
        return this.blockedThisTurnCreatures;
    }

}
