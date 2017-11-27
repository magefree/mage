/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.watchers.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
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
        super(AttackedLastTurnWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public AttackedLastTurnWatcher(final AttackedLastTurnWatcher watcher) {
        super(watcher);
        for (Entry<UUID, Set<MageObjectReference>> entry : watcher.attackedLastTurnCreatures.entrySet()) {
            Set<MageObjectReference> allAttackersCopy = new HashSet<>();
            allAttackersCopy.addAll(entry.getValue());
            attackedLastTurnCreatures.put(entry.getKey(), allAttackersCopy);
        }
        for (Entry<UUID, Set<MageObjectReference>> entry : watcher.attackedThisTurnCreatures.entrySet()) {
            Set<MageObjectReference> allAttackersCopy = new HashSet<>();
            allAttackersCopy.addAll(entry.getValue());
            attackedThisTurnCreatures.put(entry.getKey(), allAttackersCopy);
        }
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

    @Override
    public AttackedLastTurnWatcher copy() {
        return new AttackedLastTurnWatcher(this);
    }

}
