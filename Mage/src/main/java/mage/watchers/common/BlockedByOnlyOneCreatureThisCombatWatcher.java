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
import java.util.Set;
import java.util.UUID;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

/**
 *
 * @author L_J
 */
public class BlockedByOnlyOneCreatureThisCombatWatcher extends Watcher {

    private final Map<CombatGroup, UUID> blockedByOneCreature = new HashMap<>();

    public BlockedByOnlyOneCreatureThisCombatWatcher() {
        super(BlockedByOnlyOneCreatureThisCombatWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public BlockedByOnlyOneCreatureThisCombatWatcher(final BlockedByOnlyOneCreatureThisCombatWatcher watcher) {
        super(watcher);
        this.blockedByOneCreature.putAll(watcher.blockedByOneCreature);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BEGIN_COMBAT_STEP_PRE) {
            this.blockedByOneCreature.clear();
        }
        else if (event.getType() == GameEvent.EventType.BLOCKER_DECLARED) {
            CombatGroup combatGroup = game.getCombat().findGroup(event.getTargetId());
            if (combatGroup != null) {
                if (combatGroup.getBlockers().size() == 1) {
                    if (!blockedByOneCreature.containsKey(combatGroup)) {
                        blockedByOneCreature.put(combatGroup, event.getSourceId());
                    }
                    else if (blockedByOneCreature.get(combatGroup) != event.getSourceId()) {
                        blockedByOneCreature.put(combatGroup, null);
                    }
                }
                else if (combatGroup.getBlockers().size() > 1) {
                    blockedByOneCreature.put(combatGroup, null);
                }
            }
        }
    }

    public Set<CombatGroup> getBlockedOnlyByCreature(UUID creature) {
        Set<CombatGroup> combatGroups = new HashSet<>();
        for (Map.Entry<CombatGroup, UUID> entry : blockedByOneCreature.entrySet()) {
            if (entry.getValue() != null) {
                if (entry.getValue().equals(creature)) {
                    combatGroups.add(entry.getKey());
                }
            }
        }
        if (combatGroups.size() > 0) {
            return combatGroups;
        }
        return null;
    }

    @Override
    public BlockedByOnlyOneCreatureThisCombatWatcher copy() {
        return new BlockedByOnlyOneCreatureThisCombatWatcher(this);
    }
}
