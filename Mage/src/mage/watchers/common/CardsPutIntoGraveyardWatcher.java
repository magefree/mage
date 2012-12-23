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
import mage.Constants;
import mage.Constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.watchers.WatcherImpl;



/**
 * Counts amount of cards put into graveyards of players during the current turn.
 *
 *
 * @author LevelX2
 */
public class CardsPutIntoGraveyardWatcher extends WatcherImpl<CardsPutIntoGraveyardWatcher> {

    private Map<UUID, Integer> amountOfCardsThisTurn = new HashMap<UUID, Integer>();


    public CardsPutIntoGraveyardWatcher() {
        super("CardsPutIntoGraveyardWatcher", WatcherScope.GAME);
    }

    public CardsPutIntoGraveyardWatcher(final CardsPutIntoGraveyardWatcher watcher) {
        super(watcher);
        for (Entry<UUID, Integer> entry : watcher.amountOfCardsThisTurn.entrySet()) {
            amountOfCardsThisTurn.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.UNTAP_STEP_PRE) {
            reset();
        }
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent) event).getToZone() == Constants.Zone.GRAVEYARD) {
            UUID playerId = event.getPlayerId();
            if (playerId != null) {
                Integer amount = amountOfCardsThisTurn.get(playerId);
                if (amount == null) {
                    amount = new Integer(1);
                } else {
                    ++amount;
                }
                amountOfCardsThisTurn.put(playerId, amount);
            }
        }
    }

    public int getLiveGained(UUID playerId) {
        Integer amount = amountOfCardsThisTurn.get(playerId);
        if (amount != null) {
            return amount.intValue();
        }
        return 0;
    }

    @Override
    public void reset() {
        super.reset();
        amountOfCardsThisTurn.clear();
    }

    @Override
    public CardsPutIntoGraveyardWatcher copy() {
        return new CardsPutIntoGraveyardWatcher(this);
    }
}
