/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.target.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TargetCardInLibrary extends TargetCard {

    private int librarySearchLimit;

    public TargetCardInLibrary() {
        this(1, 1, new FilterCard());
    }

    public TargetCardInLibrary(FilterCard filter) {
        this(1, 1, filter);
    }

    public TargetCardInLibrary(int numTargets, FilterCard filter) {
        this(numTargets, numTargets, filter);
    }

    public TargetCardInLibrary(int minNumTargets, int maxNumTargets, FilterCard filter) {
        super(minNumTargets, maxNumTargets, Zone.LIBRARY, filter);
        // 701.15b If a player is searching a hidden zone for cards with a stated quality, such as a card
        // with a certain card type or color, that player isn’t required to find some or all of those cards
        // even if they’re present in that zone.
        this.setRequired(!filter.hasPredicates());
        this.setNotTarget(true);
        this.librarySearchLimit = Integer.MAX_VALUE;
    }

    public TargetCardInLibrary(final TargetCardInLibrary target) {
        super(target);
        this.librarySearchLimit = target.librarySearchLimit;
    }

    @Override
    public boolean choose(Outcome outcome, UUID playerId, UUID targetPlayerId, Game game) {
        Player player = game.getPlayer(playerId);
        Player targetPlayer = game.getPlayer(targetPlayerId);
        if (targetPlayer == null) {
            targetPlayer = player;
        }

        List<Card> cards;
        if (librarySearchLimit == Integer.MAX_VALUE) {
            cards = targetPlayer.getLibrary().getCards(game);
        } else {
            cards = new ArrayList<>(targetPlayer.getLibrary().getTopCards(game, librarySearchLimit));
        }
        cards.sort(new CardNameComparator());
        Cards cardsId = new CardsImpl();
        for (Card card : cards) {
            cardsId.add(card);
        }
        while (!isChosen() && !doneChosing()) {
            chosen = targets.size() >= minNumberOfTargets;
            if (!player.chooseTarget(outcome, cardsId, this, null, game)) {
                return chosen;
            }
            chosen = targets.size() >= minNumberOfTargets;
        }

        return chosen = true;
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Card card = game.getPlayer(source.getControllerId()).getLibrary().getCard(id, game);
        return card != null && filter.match(card, game);
    }

    @Override
    public TargetCardInLibrary copy() {
        return new TargetCardInLibrary(this);
    }

    @Override
    public void setMinNumberOfTargets(int minNumberOfTargets) {
        this.minNumberOfTargets = minNumberOfTargets;
    }

    public void setCardLimit(int librarySearchLimit) {
        this.librarySearchLimit = librarySearchLimit;
    }

}

class CardNameComparator implements Comparator<Card> {

    @Override
    public int compare(Card o1, Card o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
