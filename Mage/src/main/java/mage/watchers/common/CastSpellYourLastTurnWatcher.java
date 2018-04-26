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

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.*;
import java.util.Map.Entry;

/**
 * @author nantuko, BetaSteward_at_googlemail.com (spjspj)
 */
public class CastSpellYourLastTurnWatcher extends Watcher {

    private final Map<UUID, Integer> amountOfSpellsCastOnPrevTurn = new HashMap<>();
    private final Map<UUID, Integer> amountOfSpellsCastOnCurrentTurn = new HashMap<>();
    private UUID lastActivePlayer = null;

    public CastSpellYourLastTurnWatcher() {
        super(CastSpellYourLastTurnWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public CastSpellYourLastTurnWatcher(final CastSpellYourLastTurnWatcher watcher) {
        super(watcher);
        for (Entry<UUID, Integer> entry : watcher.amountOfSpellsCastOnCurrentTurn.entrySet()) {
            amountOfSpellsCastOnCurrentTurn.put(entry.getKey(), entry.getValue());
        }
        for (Entry<UUID, Integer> entry : watcher.amountOfSpellsCastOnPrevTurn.entrySet()) {
            amountOfSpellsCastOnPrevTurn.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void watch(GameEvent event, Game game) {
        lastActivePlayer = game.getActivePlayerId();
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            UUID playerId = event.getPlayerId();
            if (playerId != null && lastActivePlayer != null && playerId.equals(lastActivePlayer)) {
                amountOfSpellsCastOnCurrentTurn.putIfAbsent(playerId, 0);
                amountOfSpellsCastOnCurrentTurn.compute(playerId, (k, a) -> a + 1);
            }
        }
    }

    @Override
    public void reset() {
        if (amountOfSpellsCastOnPrevTurn != null
                && lastActivePlayer != null
                && amountOfSpellsCastOnPrevTurn.get(lastActivePlayer) != null) {
            amountOfSpellsCastOnPrevTurn.remove(lastActivePlayer);
        }

        amountOfSpellsCastOnPrevTurn.putAll(amountOfSpellsCastOnCurrentTurn);
        amountOfSpellsCastOnCurrentTurn.clear();
        lastActivePlayer = null;
    }

    public Integer getAmountOfSpellsCastOnPlayersTurn(UUID playerId) {
        return amountOfSpellsCastOnPrevTurn.getOrDefault(playerId, 0);
    }

    @Override
    public CastSpellYourLastTurnWatcher copy() {
        return new CastSpellYourLastTurnWatcher(this);
    }
}
