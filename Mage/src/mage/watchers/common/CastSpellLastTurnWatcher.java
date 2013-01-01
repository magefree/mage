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

import java.util.*;
import java.util.Map.Entry;
import mage.Constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.WatcherImpl;



/**
*
* @author nantuko, BetaSteward_at_googlemail.com
*/
public class CastSpellLastTurnWatcher extends WatcherImpl<CastSpellLastTurnWatcher> {

    private Map<UUID, Integer> amountOfSpellsCastOnPrevTurn = new HashMap<UUID, Integer>();
    private Map<UUID, Integer> amountOfSpellsCastOnCurrentTurn = new HashMap<UUID, Integer>();
    private List<UUID> spellsCastThisTurnInOrder = new ArrayList<UUID>();

    public CastSpellLastTurnWatcher() {
       super("CastSpellLastTurnWatcher", WatcherScope.GAME);
    }

    public CastSpellLastTurnWatcher(final CastSpellLastTurnWatcher watcher) {
       super(watcher);
       for (Entry<UUID, Integer> entry: watcher.amountOfSpellsCastOnCurrentTurn.entrySet()) {
           amountOfSpellsCastOnCurrentTurn.put(entry.getKey(), entry.getValue());
       }
       for (Entry<UUID, Integer> entry: watcher.amountOfSpellsCastOnPrevTurn.entrySet()) {
           amountOfSpellsCastOnPrevTurn.put(entry.getKey(), entry.getValue());
       }
    }

    @Override
    public void watch(GameEvent event, Game game) {
       if (event.getType() == GameEvent.EventType.SPELL_CAST) {
           spellsCastThisTurnInOrder.add(event.getTargetId());
           UUID playerId = event.getPlayerId();
           if (playerId != null) {
               Integer amount = amountOfSpellsCastOnCurrentTurn.get(playerId);
               if (amount == null) {
                   amount = Integer.valueOf(1);
               } else {
                   amount = Integer.valueOf(amount+1);
               }
               amountOfSpellsCastOnCurrentTurn.put(playerId, amount);
           }
       }
    }

    @Override
    public void reset() {
       amountOfSpellsCastOnPrevTurn.clear();
       amountOfSpellsCastOnPrevTurn.putAll(amountOfSpellsCastOnCurrentTurn);
       amountOfSpellsCastOnCurrentTurn.clear();
       spellsCastThisTurnInOrder.clear();
    }

    public Map<UUID, Integer> getAmountOfSpellsCastOnPrevTurn() {
       return amountOfSpellsCastOnPrevTurn;
    }

    public Map<UUID, Integer> getAmountOfSpellsCastOnCurrentTurn() {
       return amountOfSpellsCastOnCurrentTurn;
    }

    public int getAmountOfSpellsPlayerCastOnCurrentTurn(UUID playerId) {
       Integer value = amountOfSpellsCastOnCurrentTurn.get(playerId);
       if (value != null) {
           return value.intValue();
       } else {
           return 0;
       }
    }

    public int getSpellOrder(Spell spell) {
       int index = 0;
       for (UUID uuid : spellsCastThisTurnInOrder) {
           index++;
           if (spell.getId().equals(uuid)) {
               return index;
           }
       }
       return 0;
    }

    @Override
    public CastSpellLastTurnWatcher copy() {
       return new CastSpellLastTurnWatcher(this);
    }

}
