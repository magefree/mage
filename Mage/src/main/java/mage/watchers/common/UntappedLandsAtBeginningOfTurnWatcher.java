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
import mage.filter.common.FilterControlledUntappedLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

/**
 *
 * @author MTGfan
 */
public class UntappedLandsAtBeginningOfTurnWatcher extends Watcher {
    
    private final Map<UUID, Integer> untappedLandCount = new HashMap<>();
    
    public UntappedLandsAtBeginningOfTurnWatcher() {
        super("UntappedLandsAtBeginningOfTurn", WatcherScope.GAME);
    }

    public UntappedLandsAtBeginningOfTurnWatcher(final UntappedLandsAtBeginningOfTurnWatcher watcher) {
        super(watcher);
        for (Entry<UUID, Integer> entry : watcher.untappedLandCount.entrySet()) {
            untappedLandCount.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BEGINNING_PHASE
                && game.getPhase() != null) {
            UUID playerId = event.getPlayerId();
            if (playerId != null) {
                Integer amount = game.getBattlefield().getAllActivePermanents(new FilterControlledUntappedLandPermanent(), playerId, game).size();
                
                untappedLandCount.put(playerId, amount);
            }
        }
    }

    public int getUntappedLandCount(UUID playerId) {
        Integer amount = untappedLandCount.get(playerId);
        if (amount != null) {
            return amount;
        }
        return 0;
    }

    @Override
    public void reset() {
        untappedLandCount.clear();
    }

    @Override
    public UntappedLandsAtBeginningOfTurnWatcher copy() {
        return new UntappedLandsAtBeginningOfTurnWatcher(this);
    }
}
