
package mage.abilities.costs.common;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
import mage.util.CardUtil;

/**
 *
 * @author nantuko
 */
public class ExileFromGraveCost extends CostImpl {

    private final List<Card> exiledCards = new ArrayList<>();

    public ExileFromGraveCost(TargetCardInYourGraveyard target) {
        target.setNotTarget(true);
        this.addTarget(target);
        if (target.getMaxNumberOfTargets() > 1) {
            this.text = "Exile "
                    + (target.getNumberOfTargets() == 1 && target.getMaxNumberOfTargets() == Integer.MAX_VALUE ? "one or more"
                    : ((target.getNumberOfTargets() < target.getMaxNumberOfTargets() ? "up to " : ""))
                    + CardUtil.numberToText(target.getMaxNumberOfTargets()))
                    + ' ' + target.getTargetName();
        } else {
            this.text = "Exile " + target.getTargetName();
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

    public ExileFromGraveCost(TargetCardInASingleGraveyard target) {
        target.setNotTarget(true);
        this.addTarget(target);
        this.text = "Exile " + target.getTargetName();
    }

    public ExileFromGraveCost(final ExileFromGraveCost cost) {
        super(cost);
        this.exiledCards.addAll(cost.getExiledCards());
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            if (targets.choose(Outcome.Exile, controllerId, sourceId, game)) {
                for (UUID targetId : targets.get(0).getTargets()) {
                    Card card = game.getCard(targetId);
                    if (card == null || game.getState().getZone(targetId) != Zone.GRAVEYARD) {
                        return false;
                    }
                    exiledCards.add(card);
                }
                Cards cardsToExile = new CardsImpl();
                cardsToExile.addAll(exiledCards);
                controller.moveCards(cardsToExile, Zone.EXILED, ability, game);
                paid = true;
            }

        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return targets.canChoose(controllerId, game);
    }

    @Override
    public ExileFromGraveCost copy() {
        return new ExileFromGraveCost(this);
    }

    public List<Card> getExiledCards() {
        return exiledCards;
    }
}
