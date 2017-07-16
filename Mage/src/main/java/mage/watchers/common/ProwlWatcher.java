/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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

import mage.abilities.keyword.ChangelingAbility;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.*;
import java.util.Map.Entry;

/**
 * Watcher stores with which creature subtypes a player made combat damage to
 * other players during a turn.
 *
 * @author LevelX
 */
public class ProwlWatcher extends Watcher {

    private final Map<UUID, Set<SubType>> damagingSubtypes = new HashMap<>();
    private final Set<UUID> allSubtypes = new HashSet<>();

    public ProwlWatcher() {
        super(ProwlWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public ProwlWatcher(final ProwlWatcher watcher) {
        super(watcher);
        for (Entry<UUID, Set<SubType>> entry : watcher.damagingSubtypes.entrySet()) {
            damagingSubtypes.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public ProwlWatcher copy() {
        return new ProwlWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == EventType.DAMAGED_PLAYER) {
            DamagedPlayerEvent dEvent = (DamagedPlayerEvent) event;
            if (dEvent.isCombatDamage()) {
                Permanent creature = game.getPermanent(dEvent.getSourceId());
                if (creature != null && !allSubtypes.contains(creature.getControllerId())) {
                    if (creature.getAbilities().containsKey(ChangelingAbility.getInstance().getId()) || creature.isAllCreatureTypes()) {
                        allSubtypes.add(creature.getControllerId());
                    } else {
                        Set<SubType> subtypes = damagingSubtypes.getOrDefault(creature.getControllerId(), new LinkedHashSet<>());

                        subtypes.addAll(creature.getSubtype(game));
                        damagingSubtypes.put(creature.getControllerId(), subtypes);
                    }
                }
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        damagingSubtypes.clear();
        allSubtypes.clear();
    }

    public boolean hasSubtypeMadeCombatDamage(UUID playerId, SubType subtype) {
        if (allSubtypes.contains(playerId)) {
            return true;
        }
        Set<SubType> subtypes = damagingSubtypes.get(playerId);
        return subtypes != null && subtypes.contains(subtype);
    }

}
