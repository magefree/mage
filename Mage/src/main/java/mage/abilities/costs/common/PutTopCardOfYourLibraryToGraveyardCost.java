package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.cards.Card;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

public class PutTopCardOfYourLibraryToGraveyardCost extends CostImpl {

    private final int numberOfCards;
    private final Set<Card> cardsMovedToGraveyard = new LinkedHashSet<>();

    public PutTopCardOfYourLibraryToGraveyardCost() {
        this(1);
    }

    public PutTopCardOfYourLibraryToGraveyardCost(int numberOfCards) {
        this.numberOfCards = numberOfCards;
        this.text = setText();
    }

    private PutTopCardOfYourLibraryToGraveyardCost(final PutTopCardOfYourLibraryToGraveyardCost cost) {
        super(cost);
        this.numberOfCards = cost.numberOfCards;
        this.cardsMovedToGraveyard.addAll(cost.getCardsMovedToGraveyard());
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(controllerId);
        if (player != null && player.getLibrary().size() >= numberOfCards) {
            paid = true;
            this.cardsMovedToGraveyard.addAll(player.getLibrary().getTopCards(game, numberOfCards));
            player.moveCards(player.getLibrary().getTopCards(game, numberOfCards), Zone.GRAVEYARD, ability, game);
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        Player player = game.getPlayer(controllerId);
        return player != null && player.getLibrary().size() >= numberOfCards;
    }

    @Override
    public PutTopCardOfYourLibraryToGraveyardCost copy() {
        return new PutTopCardOfYourLibraryToGraveyardCost(this);
    }

    public Set<Card> getCardsMovedToGraveyard() {
        return cardsMovedToGraveyard;
    }

    private String setText() {
        return "mill " + (numberOfCards == 1 ? "a card" : CardUtil.numberToText(numberOfCards) + " cards");
    }
}
