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
package mage.sets.shadowmoor;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.watchers.Watcher;

/**
 *
 * @author jeffwadsworth
 */
public class DreamSalvage extends CardImpl {

    public DreamSalvage(UUID ownerId) {
        super(ownerId, 160, "Dream Salvage", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{U/B}");
        this.expansionSetCode = "SHM";

        this.color.setBlue(true);
        this.color.setBlack(true);

        // Draw cards equal to the number of cards target opponent discarded this turn.
        this.getSpellAbility().addEffect(new DreamSalvageEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addWatcher(new CardsDiscardedThisTurnWatcher());

    }

    public DreamSalvage(final DreamSalvage card) {
        super(card);
    }

    @Override
    public DreamSalvage copy() {
        return new DreamSalvage(this);
    }
}

class CardsDiscardedThisTurnWatcher extends Watcher {

    private final Map<UUID, Integer> amountOfCardsDiscardedThisTurn = new HashMap<>();

    public CardsDiscardedThisTurnWatcher() {
        super("CardsDiscardedThisTurnWatcher", WatcherScope.GAME);
    }

    public CardsDiscardedThisTurnWatcher(final CardsDiscardedThisTurnWatcher watcher) {
        super(watcher);
        for (Entry<UUID, Integer> entry : watcher.amountOfCardsDiscardedThisTurn.entrySet()) {
            amountOfCardsDiscardedThisTurn.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DISCARDED_CARD) {
            UUID playerId = event.getPlayerId();
            if (playerId != null) {
                Integer amount = amountOfCardsDiscardedThisTurn.get(playerId);
                if (amount == null) {
                    amount = 1;
                } else {
                    amount++;
                }
                amountOfCardsDiscardedThisTurn.put(playerId, amount);
            }
        }
    }

    public int getAmountCardsDiscarded(UUID playerId) {
        Integer amount = amountOfCardsDiscardedThisTurn.get(playerId);
        if (amount != null) {
            return amount;
        }
        return 0;
    }

    @Override
    public void reset() {
        amountOfCardsDiscardedThisTurn.clear();
    }

    @Override
    public CardsDiscardedThisTurnWatcher copy() {
        return new CardsDiscardedThisTurnWatcher(this);
    }
}

class DreamSalvageEffect extends OneShotEffect {

    public DreamSalvageEffect() {
        super(Outcome.DrawCard);
        staticText = "Draw cards equal to the number of cards target opponent discarded this turn";
    }

    public DreamSalvageEffect(final DreamSalvageEffect effect) {
        super(effect);
    }

    @Override
    public DreamSalvageEffect copy() {
        return new DreamSalvageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CardsDiscardedThisTurnWatcher watcher = (CardsDiscardedThisTurnWatcher) game.getState().getWatchers().get("CardsDiscardedThisTurnWatcher");
        Player targetOpponent = game.getPlayer(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (targetOpponent != null
                && controller != null
                && watcher != null
                && watcher.getAmountCardsDiscarded(targetOpponent.getId()) > 0) {
            controller.drawCards(watcher.getAmountCardsDiscarded(targetOpponent.getId()), game);
            return true;
        }
        return false;
    }
}
