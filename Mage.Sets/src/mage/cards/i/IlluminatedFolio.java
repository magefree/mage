
package mage.cards.i;

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
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
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
public final class IlluminatedFolio extends CardImpl {

    public IlluminatedFolio(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

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
