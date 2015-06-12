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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author jeffwadsworth
 */
public class IlluminatedFolio extends CardImpl {

    public IlluminatedFolio(UUID ownerId) {
        super(ownerId, 254, "Illuminated Folio", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{5}");
        this.expansionSetCode = "SHM";

        // {1}, {tap}, Reveal two cards from your hand that share a color: Draw a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new ManaCostsImpl("{1}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new RevealTwoCardsSharedColorFromHandCost());
        this.addAbility(ability);

    }

    public IlluminatedFolio(final IlluminatedFolio card) {
        super(card);
    }

    @Override
    public IlluminatedFolio copy() {
        return new IlluminatedFolio(this);
    }
}

class RevealTwoCardsSharedColorFromHandCost extends RevealTargetFromHandCost {

    public RevealTwoCardsSharedColorFromHandCost() {
        super(new TargetTwoCardsWithTheSameColorInHand());
    }

    public RevealTwoCardsSharedColorFromHandCost(RevealTwoCardsSharedColorFromHandCost cost) {
        super(cost);
    }

    @Override
    public RevealTwoCardsSharedColorFromHandCost copy() {
        return new RevealTwoCardsSharedColorFromHandCost(this);
    }

}

class TargetTwoCardsWithTheSameColorInHand extends TargetCardInHand {

    public TargetTwoCardsWithTheSameColorInHand() {
        super(2, 2, new FilterCard("two cards from your hand that share a color"));
    }

    public TargetTwoCardsWithTheSameColorInHand(final TargetTwoCardsWithTheSameColorInHand target) {
        super(target);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        Set<UUID> newPossibleTargets = new HashSet<>();
        Set<UUID> possibleTargets = new HashSet<>();
        Player player = game.getPlayer(sourceControllerId);
        for (Card card : player.getHand().getCards(filter, game)) {
            possibleTargets.add(card.getId());
        }

        Cards cardsToCheck = new CardsImpl();
        cardsToCheck.addAll(possibleTargets);
        if (targets.size() == 1) {
            // first target is already choosen, now only targets with the shared color are selectable
            for (Map.Entry<UUID, Integer> entry : targets.entrySet()) {
                Card chosenCard = cardsToCheck.get(entry.getKey(), game);
                if (chosenCard != null) {
                    for (UUID cardToCheck : cardsToCheck) {
                        if (!cardToCheck.equals(chosenCard.getId()) && chosenCard.getColor(game).equals(game.getCard(cardToCheck).getColor(game))) {
                            newPossibleTargets.add(cardToCheck);
                        }
                    }
                }
            }
        } else {
            for (UUID cardToCheck : cardsToCheck) {
                FilterCard colorFilter = new FilterCard();
                colorFilter.add(new ColorPredicate(game.getCard(cardToCheck).getColor(game)));
                if (cardsToCheck.count(colorFilter, game) > 1) {
                    newPossibleTargets.add(cardToCheck);
                }
            }
        }
        return newPossibleTargets;
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Game game) {
        Cards cardsToCheck = new CardsImpl();
        Player player = game.getPlayer(sourceControllerId);
        for (Card card : player.getHand().getCards(filter, game)) {
            cardsToCheck.add(card.getId());
        }
        int possibleCards = 0;
        for (UUID cardToCheck : cardsToCheck) {
            FilterCard colorFilter = new FilterCard();
            colorFilter.add(new ColorPredicate(game.getCard(cardToCheck).getColor(game)));
            if (cardsToCheck.count(colorFilter, game) > 1) {
                ++possibleCards;
            }
        }
        return possibleCards > 0;
    }

    @Override
    public boolean canTarget(UUID id, Game game) {
        if (super.canTarget(id, game)) {
            Card card = game.getCard(id);
            if (card != null) {
                if (targets.size() == 1) {
                    Card card2 = game.getCard(targets.entrySet().iterator().next().getKey());
                    if (card2 != null && card2.getColor(game).equals(card.getColor(game))) {
                        return true;
                    }
                } else {
                    FilterCard colorFilter = new FilterCard();
                    colorFilter.add(new ColorPredicate(card.getColor(game)));
                    Player player = game.getPlayer(card.getOwnerId());
                    if (player.getHand().getCards(colorFilter, game).size() > 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public TargetTwoCardsWithTheSameColorInHand copy() {
        return new TargetTwoCardsWithTheSameColorInHand(this);
    }
}
