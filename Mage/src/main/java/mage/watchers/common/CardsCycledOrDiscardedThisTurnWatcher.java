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
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import mage.MageObjectReference;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

/**
 * Stores cards that were cycled or discarded by any player this turn.
 *
 * @author jeffwadsworth
 */
public class CardsCycledOrDiscardedThisTurnWatcher extends Watcher {

    private final Map<UUID, Cards> cycledOrDiscardedCardsThisTurn = new HashMap<>();
    private final Map<UUID, Set<MageObjectReference>> numberOfCycledOrDiscardedCardsThisTurn = new HashMap<>();

    public CardsCycledOrDiscardedThisTurnWatcher() {
        super(CardsCycledOrDiscardedThisTurnWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public CardsCycledOrDiscardedThisTurnWatcher(final CardsCycledOrDiscardedThisTurnWatcher watcher) {
        super(watcher);
        for (Entry<UUID, Cards> entry : watcher.cycledOrDiscardedCardsThisTurn.entrySet()) {
            cycledOrDiscardedCardsThisTurn.put(entry.getKey(), entry.getValue().copy());
        }
        for (Entry<UUID, Set<MageObjectReference>> entry : watcher.numberOfCycledOrDiscardedCardsThisTurn.entrySet()) {
            Set<MageObjectReference> cycledOrDiscardedCards = new HashSet<>();
            cycledOrDiscardedCards.addAll(entry.getValue());
            numberOfCycledOrDiscardedCardsThisTurn.put(entry.getKey(), cycledOrDiscardedCards);
        }
        numberOfCycledOrDiscardedCardsThisTurn.putAll(watcher.numberOfCycledOrDiscardedCardsThisTurn);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DISCARDED_CARD
                || event.getType() == GameEvent.EventType.CYCLED_CARD
                && event.getPlayerId() != null) {
            Card card = game.getCard(event.getTargetId());
            if (card != null) {
                Cards c = getCardsCycledOrDiscardedThisTurn(event.getPlayerId());
                c.add(card);
                cycledOrDiscardedCardsThisTurn.put(event.getPlayerId(), c);
                Set<MageObjectReference> cycledOrDiscardedCards = numberOfCycledOrDiscardedCardsThisTurn.get(event.getPlayerId());
                if (cycledOrDiscardedCards == null) {
                    cycledOrDiscardedCards = new HashSet<>();
                    numberOfCycledOrDiscardedCardsThisTurn.put(event.getPlayerId(), cycledOrDiscardedCards);
                }
                cycledOrDiscardedCards.add(new MageObjectReference(card, game));
            }
        }
    }

    public Cards getCardsCycledOrDiscardedThisTurn(UUID playerId) {
        return cycledOrDiscardedCardsThisTurn.getOrDefault(playerId, new CardsImpl());
    }

    public int getNumberOfCardsCycledOrDiscardedThisTurn(UUID playerId) {
        if (numberOfCycledOrDiscardedCardsThisTurn.containsKey(playerId)) {
            return numberOfCycledOrDiscardedCardsThisTurn.get(playerId).size();
        }
        return 0;
    }

    @Override
    public void reset() {
        super.reset();
        cycledOrDiscardedCardsThisTurn.clear();
        numberOfCycledOrDiscardedCardsThisTurn.clear();
    }

    @Override
    public CardsCycledOrDiscardedThisTurnWatcher copy() {
        return new CardsCycledOrDiscardedThisTurnWatcher(this);
    }
}
