package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SphinxOfTheChimes extends CardImpl {

    public SphinxOfTheChimes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");
        this.subtype.add(SubType.SPHINX);

        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Discard two nonland cards with the same name: Draw four cards.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(4), new DiscardTwoNonlandCardsWithTheSameNameCost());
        this.addAbility(ability);

    }

    private SphinxOfTheChimes(final SphinxOfTheChimes card) {
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
        super(2, 2, new FilterNonlandCard("nonland cards with the same name"));
    }

    public TargetTwoNonLandCardsWithSameNameInHand(final TargetTwoNonLandCardsWithSameNameInHand target) {
        super(target);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        Set<UUID> newPossibleTargets = new HashSet<>();
        Set<UUID> possibleTargets = new HashSet<>();
        Player player = game.getPlayer(sourceControllerId);
        if (player == null) {
            return newPossibleTargets;
        }
        for (Card card : player.getHand().getCards(filter, game)) {
            possibleTargets.add(card.getId());
        }

        Cards cardsToCheck = new CardsImpl();
        cardsToCheck.addAll(possibleTargets);
        if (targets.size() == 1) {
            // first target is laready chosen, now only targets with the same name are selectable
            for (Map.Entry<UUID, Integer> entry : targets.entrySet()) {
                Card chosenCard = cardsToCheck.get(entry.getKey(), game);
                if (chosenCard != null) {
                    for (UUID cardToCheck : cardsToCheck) {
                        if (!cardToCheck.equals(chosenCard.getId()) && chosenCard.getName().equals(game.getCard(cardToCheck).getName())) {
                            newPossibleTargets.add(cardToCheck);
                        }
                    }
                }
            }
        } else {
            for (UUID cardToCheck : cardsToCheck) {
                Card card = game.getCard(cardToCheck);
                if (card != null) {
                    String nameToSearch = CardUtil.getCardNameForSameNameSearch(card);
                    FilterCard nameFilter = new FilterCard();
                    nameFilter.add(new NamePredicate(nameToSearch));

                    if (cardsToCheck.count(nameFilter, game) > 1) {
                        newPossibleTargets.add(cardToCheck);
                    }
                }
            }
        }
        return newPossibleTargets;
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Game game) {
        Cards cardsToCheck = new CardsImpl();
        Player player = game.getPlayer(sourceControllerId);
        if (player == null) {
            return false;
        }
        for (Card card : player.getHand().getCards(filter, game)) {
            cardsToCheck.add(card.getId());
        }
        int possibleCards = 0;
        for (Card card : cardsToCheck.getCards(game)) {
            String nameToSearch = CardUtil.getCardNameForSameNameSearch(card);
            FilterCard nameFilter = new FilterCard();
            nameFilter.add(new NamePredicate(nameToSearch));

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
                    return CardUtil.haveSameNames(card2, card);
                } else {
                    String nameToSearch = CardUtil.getCardNameForSameNameSearch(card);
                    FilterCard nameFilter = new FilterCard();
                    nameFilter.add(new NamePredicate(nameToSearch));

                    Player player = game.getPlayer(card.getOwnerId());
                    return player != null && player.getHand().getCards(nameFilter, game).size() > 1;
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
