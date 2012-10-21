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
package mage.sets.returntoravnica;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public class SphinxOfTheChimes extends CardImpl<SphinxOfTheChimes> {

    public SphinxOfTheChimes(UUID ownerId) {
        super(ownerId, 52, "Sphinx of the Chimes", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");
        this.expansionSetCode = "RTR";
        this.subtype.add("Bird");

        this.color.setBlue(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Discard two nonland cards with the same name: Draw four cards.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new DrawCardControllerEffect(4), new DiscardTwoNonlandCardsWithTheSameNameCost());
        this.addAbility(ability);

    }

    public SphinxOfTheChimes(final SphinxOfTheChimes card) {
        super(card);
    }

    @Override
    public SphinxOfTheChimes copy() {
        return new SphinxOfTheChimes(this);
    }
}

class DiscardTwoNonlandCardsWithTheSameNameCost extends DiscardTargetCost {

    public DiscardTwoNonlandCardsWithTheSameNameCost() {
        super(new TargetTwoNonLandCardsWithSameNameInHand());
    }

    public DiscardTwoNonlandCardsWithTheSameNameCost(DiscardTwoNonlandCardsWithTheSameNameCost cost) {
        super(cost);
    }

    @Override
    public DiscardTwoNonlandCardsWithTheSameNameCost copy() {
        return new DiscardTwoNonlandCardsWithTheSameNameCost(this);
    }

}

class TargetTwoNonLandCardsWithSameNameInHand extends TargetCardInHand {

    public TargetTwoNonLandCardsWithSameNameInHand() {
        super(2, 2, new FilterNonlandCard("two nonland cards with the same name"));
    }

    public TargetTwoNonLandCardsWithSameNameInHand(final TargetTwoNonLandCardsWithSameNameInHand target) {
        super(target);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        Set<UUID> newPossibleTargets = new HashSet<UUID>();
        Set<UUID> possibleTargets = new HashSet<UUID>();
        Player player = game.getPlayer(sourceControllerId);
        for (Card card : player.getHand().getCards(filter, game)) {
            possibleTargets.add(card.getId());
        }

        Cards cardsToCheck = new CardsImpl();
        cardsToCheck.addAll(possibleTargets);
        if (targets.size() == 1) {
            // first target is laready choosen, now only targets with the same name are selectable
            for (Map.Entry<UUID, Integer> entry : targets.entrySet()) {
                Card chosenCard = cardsToCheck.get(entry.getKey(), game);
                if (chosenCard != null) {
                    for (UUID cardToCheck : cardsToCheck) {
                        if (!cardToCheck.equals(chosenCard.getId()) && chosenCard.getName().equals(game.getCard(cardToCheck).getName()))
                        {
                            newPossibleTargets.add(cardToCheck);
                        }
                    }
                }
            }
        } 
        else
        {
            for (UUID cardToCheck : cardsToCheck) {
                FilterCard nameFilter = new FilterCard();
                nameFilter.add(new NamePredicate(game.getCard(cardToCheck).getName()));
                if (cardsToCheck.count(nameFilter, game) > 1) {
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
            FilterCard nameFilter = new FilterCard();
            nameFilter.add(new NamePredicate(game.getCard(cardToCheck).getName()));
            if (cardsToCheck.count(nameFilter, game) > 1) {
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
                    if (card2 != null && card2.getName().equals(card.getName())) {
                        return true;
                    }
                } else {
                    FilterCard nameFilter = new FilterCard();
                    nameFilter.add(new NamePredicate(card.getName()));
                    Player player = game.getPlayer(card.getOwnerId());
                    if (player.getHand().getCards(nameFilter, game).size() > 1) {
                        return true;
                   }
                }
            }
        }
        return false;
    }

    @Override
    public TargetTwoNonLandCardsWithSameNameInHand copy() {
        return new TargetTwoNonLandCardsWithSameNameInHand(this);
    }
}
