package mage.abilities.costs.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInASingleGraveyard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author nantuko
 */
public class ExileManaValueFromGraveyardCost extends CostImpl {

    private final List<Card> exiledCards = new ArrayList<>();
    private boolean setTargetPointer = false;
    private final int totalManaValue;

    public ExileManaValueFromGraveyardCost(TargetCardInYourGraveyard target, int totalManaValue) {
        target.withNotTarget(true);
        this.addTarget(target);
        this.totalManaValue = totalManaValue;

        this.text = "Exile any number of " + target.getFilter().getMessage() + " from your graveyard with total mana value " + totalManaValue + " or greater";
    }

    protected ExileManaValueFromGraveyardCost(final ExileManaValueFromGraveyardCost cost) {
        super(cost);
        this.exiledCards.addAll(cost.getExiledCards());
        this.setTargetPointer = cost.setTargetPointer;
        this.totalManaValue = cost.totalManaValue;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            if (this.getTargets().choose(Outcome.Exile, controllerId, source.getSourceId(), source, game)) {
                for (UUID targetId : this.getTargets().get(0).getTargets()) {
                    Card card = game.getCard(targetId);
                    if (card == null
                            || game.getState().getZone(targetId) != Zone.GRAVEYARD) {
                        return false;
                    }
                    exiledCards.add(card);
                }
                Cards cardsToExile = new CardsImpl();
                cardsToExile.addAllCards(exiledCards);
                controller.moveCardsToExile(
                        cardsToExile.getCards(game), source, game, true,
                        CardUtil.getExileZoneId(game, source),
                        CardUtil.getSourceName(game, source)
                );
                paid = true;
            }

        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return this.getTargets().canChoose(controllerId, source, game)
                && this.getTargets().get(0).getTargets().stream().map(game::getCard).mapToInt(MageObject::getManaValue).sum() >= this.totalManaValue;
    }

    @Override
    public ExileManaValueFromGraveyardCost copy() {
        return new ExileManaValueFromGraveyardCost(this);
    }

    public List<Card> getExiledCards() {
        return exiledCards;
    }
}
