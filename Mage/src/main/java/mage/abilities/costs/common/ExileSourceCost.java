
package mage.abilities.costs.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.cards.Card;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ExileSourceCost extends CostImpl {

    private final boolean toUniqueExileZone;

    public ExileSourceCost() {
        this(false);
    }

    /**
     * @param toUniqueExileZone moves the card to a source object dependant
     *                          unique exile zone, so another effect of the same source object (e.g.
     *                          Deadeye Navigator) can identify the card
     */
    public ExileSourceCost(boolean toUniqueExileZone) {
        this.text = "exile {this}";
        this.toUniqueExileZone = toUniqueExileZone;
    }

    public ExileSourceCost(ExileSourceCost cost) {
        super(cost);
        this.toUniqueExileZone = cost.toUniqueExileZone;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        MageObject sourceObject = ability.getSourceObject(game);
        Player controller = game.getPlayer(controllerId);
        if (controller != null && sourceObject instanceof Card) {
            UUID exileZoneId = null;
            String exileZoneName = "";
            if (toUniqueExileZone) {
                exileZoneId = CardUtil.getExileZoneId(game, ability.getSourceId(), ability.getSourceObjectZoneChangeCounter());
                exileZoneName = sourceObject.getName();
                game.getState().setValue(sourceObject.getId().toString(), ability.getSourceObjectZoneChangeCounter());
            }
            controller.moveCardToExileWithInfo((Card) sourceObject, exileZoneId, exileZoneName, source, game, game.getState().getZone(sourceObject.getId()), true);
            // 117.11. The actions performed when paying a cost may be modified by effects.
            // Even if they are, meaning the actions that are performed don't match the actions
            // that are called for, the cost has still been paid.
            // so return state here is not important because the user indended to exile the target anyway
            paid = true;
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return source.getSourceObjectIfItStillExists(game) instanceof Card;
    }

    @Override
    public ExileSourceCost copy() {
        return new ExileSourceCost(this);
    }

}
