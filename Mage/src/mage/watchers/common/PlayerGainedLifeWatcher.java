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
import mage.Constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.WatcherImpl;



/**
 * Counts amount of life gained during the current turn by players.
 *
 *
 * @author LevelX2
 */
public class PlayerGainedLifeWatcher extends WatcherImpl<PlayerGainedLifeWatcher> {

    private Map<UUID, Integer> amountOfLifeGainedThisTurn = new HashMap<UUID, Integer>();


    public PlayerGainedLifeWatcher() {
        super("PlayerGainedLifeWatcher", WatcherScope.GAME);
    }

    public PlayerGainedLifeWatcher(final PlayerGainedLifeWatcher watcher) {
        super(watcher);
        for (Entry<UUID, Integer> entry : watcher.amountOfLifeGainedThisTurn.entrySet()) {
            amountOfLifeGainedThisTurn.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.UNTAP_STEP_PRE) {
            reset();
        }
        if (event.getType() == GameEvent.EventType.GAINED_LIFE) {
            UUID playerId = event.getPlayerId();
            if (playerId != null) {
                Integer amount = amountOfLifeGainedThisTurn.get(playerId);
                if (amount == null) {
                    amount = Integer.valueOf(event.getAmount());
                } else {
                    amount = Integer.valueOf(amount + event.getAmount());
                }
                amountOfLifeGainedThisTurn.put(playerId, amount);
            }
        }
    }

    public int getLiveGained(UUID playerId) {
        Integer amount = amountOfLifeGainedThisTurn.get(playerId);
        if (amount != null) {
            return amount.intValue();
        }
        return 0;
    }

    @Override
    public void reset() {
        super.reset();
        amountOfLifeGainedThisTurn.clear();
    }

    @Override
    public PlayerGainedLifeWatcher copy() {
        return new PlayerGainedLifeWatcher(this);
    }
}
