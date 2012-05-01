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

import mage.Constants;
import mage.Constants.WatcherScope;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.keyword.MiracleAbility;
import mage.cards.Card;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.watchers.WatcherImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

/**
 * Counts amount of cards drawn this turn by players.
 * Asks players about Miracle ability to be activated if it the first card drawn this turn.
 *
 * @author noxx
 */
public class MiracleWatcher extends WatcherImpl<MiracleWatcher> {

    private Map<UUID, Integer> amountOfCardsDrawnThisTurn = new HashMap<UUID, Integer>();

    public MiracleWatcher() {
        super("MiracleWatcher", WatcherScope.GAME);
    }

    public MiracleWatcher(final MiracleWatcher watcher) {
        super(watcher);
        for (Entry<UUID, Integer> entry : watcher.amountOfCardsDrawnThisTurn.entrySet()) {
            amountOfCardsDrawnThisTurn.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DREW_CARD) {
            UUID playerId = event.getPlayerId();
            if (playerId != null) {
                Integer amount = amountOfCardsDrawnThisTurn.get(playerId);
                if (amount == null) {
                    amount = Integer.valueOf(1);
                } else {
                    amount = Integer.valueOf(amount + 1);
                }
                amountOfCardsDrawnThisTurn.put(playerId, amount);
                if (amount == 1) {
                    checkMiracleAbility(event, game);
                }
            }
        }
    }

    private void checkMiracleAbility(GameEvent event, Game game) {
        Card card = game.getCard(event.getTargetId());
        if (card != null) {
            for (Ability ability : card.getAbilities()) {
                if (ability instanceof MiracleAbility) {
                    Player controller = game.getPlayer(ability.getControllerId());
                    ManaCosts<ManaCost> manaCostsToPay = ability.getManaCostsToPay();
                    if (controller != null && controller.chooseUse(Constants.Outcome.Benefit, "Use Miracle " + manaCostsToPay.getText() + "?", game)) {
                        if (manaCostsToPay.pay(ability, game, ability.getSourceId(), ability.getControllerId(), false)) {
                            controller.cast(card.getSpellAbility(), game, true);
                        }
                        break;
                    }
                }
            }
        }
    }


    @Override
    public void reset() {
        amountOfCardsDrawnThisTurn.clear();
    }

    @Override
    public MiracleWatcher copy() {
        return new MiracleWatcher(this);
    }
}
