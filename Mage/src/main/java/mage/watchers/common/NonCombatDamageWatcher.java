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

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.watchers.Watcher;

/*
 *
 * @author Styxo
 */
public class NonCombatDamageWatcher extends Watcher {

    private final Set<UUID> playersBeenDealtNonCombatDamage = new HashSet<>();

    public NonCombatDamageWatcher() {
        super("NonCombatDamageWatcher", WatcherScope.GAME);
    }

    public NonCombatDamageWatcher(final NonCombatDamageWatcher watcher) {
        super(watcher);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.LOST_LIFE) {
            UUID playerId = event.getPlayerId();
            if (playerId != null) {
                playersBeenDealtNonCombatDamage.add(playerId);
            }
        } else if (event.getType() == EventType.DAMAGED_PLAYER) {
            DamagedPlayerEvent dEvent = (DamagedPlayerEvent) event;
            if (!dEvent.isCombatDamage()) {
                UUID playerId = event.getPlayerId();
                if (playerId != null) {
                    playersBeenDealtNonCombatDamage.add(playerId);
                }
            } 
        }
    }

    public boolean opponentsBeenDealtNonCombatDamage(UUID playerId) {
        return (!playersBeenDealtNonCombatDamage.contains(playerId) && playersBeenDealtNonCombatDamage.size() > 0)
                || (playersBeenDealtNonCombatDamage.contains(playerId) && playersBeenDealtNonCombatDamage.size() > 1);
    }

    @Override
    public void reset() {
        super.reset();
        playersBeenDealtNonCombatDamage.clear();
    }

    @Override
    public NonCombatDamageWatcher copy() {
        return new NonCombatDamageWatcher(this);
    }
}
