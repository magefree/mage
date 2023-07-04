package mage.abilities.costs.common;

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

/**
 * @author nantuko
 */
public class ExileFromGraveCost extends CostImpl {

    private final List<Card> exiledCards = new ArrayList<>();
    private boolean setTargetPointer = false;

    public ExileFromGraveCost(TargetCardInYourGraveyard target) {
        target.setNotTarget(true);
        this.addTarget(target);
        if (target.getMaxNumberOfTargets() > 1) {
            this.text = "exile "
                    + (target.getNumberOfTargets() == 1
                    && target.getMaxNumberOfTargets() == Integer.MAX_VALUE ? "one or more"
                    : ((target.getNumberOfTargets() < target.getMaxNumberOfTargets() ? "up to " : ""))
                    + CardUtil.numberToText(target.getMaxNumberOfTargets()))
                    + ' ' + target.getTargetName();
        } else {
            this.text = "exile " + CardUtil.addArticle(target.getTargetName().replace("cards ", "card "));
        }
        if (!this.text.endsWith(" from your graveyard")) {
            this.text = this.text + " from your graveyard";
        }
    }

    public ExileFromGraveCost(TargetCardInYourGraveyard target, String text) {
        target.setNotTarget(true);
        this.addTarget(target);
        this.text = text;
    }

    public ExileFromGraveCost(TargetCardInASingleGraveyard target, String text) {
        target.setNotTarget(true);
        this.addTarget(target);
        this.text = text;
    }

    public ExileFromGraveCost(TargetCardInASingleGraveyard target) {
        target.setNotTarget(true);
        this.addTarget(target);
        this.text = "exile " + target.getDescription();
    }

    public ExileFromGraveCost(TargetCardInYourGraveyard target, boolean setTargetPointer) {
        this(target);
        this.setTargetPointer = setTargetPointer;
    }

    public ExileFromGraveCost(final ExileFromGraveCost cost) {
        super(cost);
        this.exiledCards.addAll(cost.getExiledCards());
        this.setTargetPointer = cost.setTargetPointer;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            if (targets.choose(Outcome.Exile, controllerId, source.getSourceId(), source, game)) {
                for (UUID targetId : targets.get(0).getTargets()) {
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
                if (setTargetPointer) {
                    source.getEffects().setTargetPointer(new FixedTargets(cardsToExile, game));
                }
                paid = true;
            }

        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return targets.canChoose(controllerId, source, game);
    }

    @Override
    public ExileFromGraveCost copy() {
        return new ExileFromGraveCost(this);
    }

    public List<Card> getExiledCards() {
        return exiledCards;
    }
}
