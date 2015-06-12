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
 * Counts amount of life lost current or last turn by players.
 *
 *
 * @author LevelX2
 */
public class PlayerLostLifeWatcher extends Watcher {

    private final Map<UUID, Integer> amountOfLifeLostThisTurn = new HashMap<>();
    private final Map<UUID, Integer> amountOfLifeLostLastTurn = new HashMap<>();

    public PlayerLostLifeWatcher() {
        super("PlayerLostLifeWatcher", WatcherScope.GAME);
    }

    public PlayerLostLifeWatcher(final PlayerLostLifeWatcher watcher) {
        super(watcher);
        for (Entry<UUID, Integer> entry : watcher.amountOfLifeLostThisTurn.entrySet()) {
            amountOfLifeLostThisTurn.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.LOST_LIFE) {
            UUID playerId = event.getPlayerId();
            if (playerId != null) {
                Integer amount = amountOfLifeLostThisTurn.get(playerId);
                if (amount == null) {
                    amount = event.getAmount();
                } else {
                    amount = amount + event.getAmount();
                }
                amountOfLifeLostThisTurn.put(playerId, amount);
            }
        }
    }

    public int getLiveLost(UUID playerId) {
        Integer amount = amountOfLifeLostThisTurn.get(playerId);
        if (amount != null) {
            return amount;
        }
        return 0;
    }

    public int getLiveLostLastTurn(UUID playerId) {
        Integer amount = amountOfLifeLostLastTurn.get(playerId);
        if (amount != null) {
            return amount;
        }
        return 0;
    }

    @Override
    public void reset() {
        amountOfLifeLostLastTurn.clear();
        amountOfLifeLostLastTurn.putAll(amountOfLifeLostThisTurn);
        amountOfLifeLostThisTurn.clear();
    }

    @Override
    public PlayerLostLifeWatcher copy() {
        return new PlayerLostLifeWatcher(this);
    }
}
