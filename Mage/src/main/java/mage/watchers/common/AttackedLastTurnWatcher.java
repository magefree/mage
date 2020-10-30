
package mage.watchers.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.MageObjectReference;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

/**
 *
 * @author L_J
 */
public class AttackedLastTurnWatcher extends Watcher {

    public final Map<UUID, Set<MageObjectReference>> attackedLastTurnCreatures = new HashMap<>(); // Map<lastTurnOfPlayerId, Set<attackingCreature>>
    public final Map<UUID, Set<MageObjectReference>> attackedThisTurnCreatures = new HashMap<>(); // dummy map for beginning of turn iteration purposes

    public AttackedLastTurnWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BEGINNING_PHASE_PRE) {
            UUID activePlayer = game.getActivePlayerId();
            if (attackedThisTurnCreatures.containsKey(activePlayer)) {
                if (attackedThisTurnCreatures.get(activePlayer) != null) {
                    attackedLastTurnCreatures.put(activePlayer, getAttackedThisTurnCreatures(activePlayer));
                }
                attackedThisTurnCreatures.remove(activePlayer);
            } else { // } else if (attackedLastTurnCreatures.containsKey(activePlayer)) {
                attackedLastTurnCreatures.remove(activePlayer);
            }
        }
        if (event.getType() == GameEvent.EventType.DECLARED_ATTACKERS) {
            UUID attackingPlayer = game.getCombat().getAttackingPlayerId();
            Set<MageObjectReference> attackingCreatures = getAttackedThisTurnCreatures(attackingPlayer);
            for (UUID attackerId : game.getCombat().getAttackers()) {
                Permanent attacker = game.getPermanent(attackerId);
                if (attacker != null) {
                    attackingCreatures.add(new MageObjectReference(attacker, game));
                }
            }
            attackedThisTurnCreatures.put(attackingPlayer, attackingCreatures);
        }
    }

    public Set<MageObjectReference> getAttackedLastTurnCreatures(UUID combatPlayerId) {
        if (attackedLastTurnCreatures.get(combatPlayerId) != null) {
            return attackedLastTurnCreatures.get(combatPlayerId);
        }
        return new HashSet<>();
    }

    public Set<MageObjectReference> getAttackedThisTurnCreatures(UUID combatPlayerId) {
        if (attackedThisTurnCreatures.get(combatPlayerId) != null) {
            return attackedThisTurnCreatures.get(combatPlayerId);
        }
        return new HashSet<>();
    }

}
