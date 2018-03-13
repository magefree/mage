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
import java.util.UUID;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;
import mage.players.PlayerList;

/**
 * @author spjspj
 */
public class PlayersAttackedLastTurnWatcher extends Watcher {

    // Store how many players each player attacked in their last turn 
    private final Map<UUID, PlayerList> playersAttackedInLastTurn = new HashMap<>();

    public PlayersAttackedLastTurnWatcher() {
        super(PlayersAttackedLastTurnWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public PlayersAttackedLastTurnWatcher(final PlayersAttackedLastTurnWatcher watcher) {
        super(watcher);
        for (Map.Entry<UUID, PlayerList> entry : watcher.playersAttackedInLastTurn.entrySet()) {
            this.playersAttackedInLastTurn.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public PlayersAttackedLastTurnWatcher copy() {
        return new PlayersAttackedLastTurnWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BEGINNING_PHASE_PRE) {
            playersAttackedInLastTurn.remove(event.getPlayerId());
        }

        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED) {
            PlayerList playersAttacked = playersAttackedInLastTurn.get(event.getPlayerId());
            if (playersAttacked == null) {
                playersAttacked = new PlayerList();
            }
            UUID defender = game.getCombat().getDefendingPlayerId(event.getSourceId(), game);
            if (defender != null) {
                playersAttacked.add(defender);
            }
            playersAttackedInLastTurn.put(event.getPlayerId(), playersAttacked);
        }
    }

    public boolean attackedLastTurn(UUID playerId, UUID otherPlayerId) {
        if (playersAttackedInLastTurn.get(playerId) != null) {
            return playersAttackedInLastTurn.get(playerId).contains(otherPlayerId);
        }
        return false;
    }
}
