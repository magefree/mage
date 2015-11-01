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
// author jeffwadsworth
package mage.abilities.costs.common;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.CostImpl;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

public class RevealTargetFromHandCost extends CostImpl {

    public int convertedManaCosts = 0;
    protected int numberCardsRevealed = 0;
    protected List<Card> revealedCards;

    public RevealTargetFromHandCost(TargetCardInHand target) {
        this.addTarget(target);
        this.text = (target.getNumberOfTargets() == 0 ? "you may " : "") + "reveal " + target.getTargetName();
        this.revealedCards = new ArrayList<>();
    }

    public RevealTargetFromHandCost(final RevealTargetFromHandCost cost) {
        super(cost);
        this.convertedManaCosts = cost.convertedManaCosts;
        this.numberCardsRevealed = cost.numberCardsRevealed;
        this.revealedCards = new ArrayList<>(cost.revealedCards);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        if (targets.choose(Outcome.Benefit, controllerId, sourceId, game)) {
            convertedManaCosts = 0;
            numberCardsRevealed = 0;
            Player player = game.getPlayer(controllerId);
            Cards cards = new CardsImpl();
            for (UUID targetId : targets.get(0).getTargets()) {
                Card card = player.getHand().get(targetId, game);
                if (card != null) {
                    convertedManaCosts += card.getManaCost().convertedManaCost();
                    numberCardsRevealed++;
                    cards.add(card);
                    revealedCards.add(card);
                }
            }
            if (numberCardsRevealed > 0) {
                MageObject baseObject = game.getBaseObject(sourceId);
                player.revealCards(baseObject == null ? "card cost" : baseObject.getIdName(), cards, game);
            }
            if (targets.get(0).getNumberOfTargets() <= numberCardsRevealed) {
                paid = true; // e.g. for optional additional costs.  example: Dragonlord's Prerogative also true if 0 cards shown
                return paid;
            }
        }
        paid = false;
        return paid;
    }

    public int getConvertedCosts() {
        return convertedManaCosts;
    }

    public int getNumberRevealedCards() {
        return numberCardsRevealed;
    }

    public List<Card> getRevealedCards() {
        return revealedCards;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return targets.canChoose(controllerId, game);
    }

    @Override
    public RevealTargetFromHandCost copy() {
        return new RevealTargetFromHandCost(this);
    }
}
