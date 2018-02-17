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
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

/**
 * @author jeffwadsworth
 *
 *    Return the last player that was attacked by specified creature this turn
 */
public class CreatureAttackedWhichPlayerWatcher extends Watcher {

    private final Map<UUID, UUID> getPlayerAttackedThisTurnByCreature = new HashMap<>();

    public CreatureAttackedWhichPlayerWatcher() {
        super(CreatureAttackedWhichPlayerWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public CreatureAttackedWhichPlayerWatcher(final CreatureAttackedWhichPlayerWatcher watcher) {
        super(watcher);
        for (Entry<UUID, UUID> entry : watcher.getPlayerAttackedThisTurnByCreature.entrySet()) {
            getPlayerAttackedThisTurnByCreature.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED) {
            UUID creatureId = event.getSourceId();
            UUID playerId = event.getTargetId();
            if (playerId != null
                    && creatureId != null) {
                getPlayerAttackedThisTurnByCreature.putIfAbsent(creatureId, playerId);
            }
        }
    }

    public UUID getPlayerAttackedThisTurnByCreature(UUID creatureId) {
        return getPlayerAttackedThisTurnByCreature.getOrDefault(creatureId, null);
    }

    @Override
    public void reset() {
        getPlayerAttackedThisTurnByCreature.clear();
    }

    @Override
    public CreatureAttackedWhichPlayerWatcher copy() {
        return new CreatureAttackedWhichPlayerWatcher(this);
    }
}
