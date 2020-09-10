package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.cards.Card;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

public class MillCardsCost extends CostImpl {

    private final int numberOfCards;
    private final Set<Card> cardsMovedToGraveyard = new LinkedHashSet<>();

    public MillCardsCost() {
        this(1);
    }

    public MillCardsCost(int numberOfCards) {
        this.numberOfCards = numberOfCards;
        this.text = setText();
    }

    private MillCardsCost(final MillCardsCost cost) {
        super(cost);
        this.numberOfCards = cost.numberOfCards;
        this.cardsMovedToGraveyard.addAll(cost.getCardsMovedToGraveyard());
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(controllerId);
        if (player != null && player.getLibrary().size() >= numberOfCards) {
            paid = true;
            this.cardsMovedToGraveyard.addAll(player.millCards(numberOfCards, ability, game).getCards(game));
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        Player player = game.getPlayer(controllerId);
        return player != null && player.getLibrary().size() >= numberOfCards;
    }

    @Override
    public MillCardsCost copy() {
        return new MillCardsCost(this);
    }

    public Set<Card> getCardsMovedToGraveyard() {
        return cardsMovedToGraveyard;
    }

    private String setText() {
        return "mill " + (numberOfCards == 1 ? "a card" : CardUtil.numberToText(numberOfCards) + " cards");
    }
}
