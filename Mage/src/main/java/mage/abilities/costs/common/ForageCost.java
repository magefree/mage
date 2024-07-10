package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.OrCost;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class ForageCost extends OrCost {

    public ForageCost() {
        super(
                "forage",
                new ExileFromGraveCost(new TargetCardInYourGraveyard(3)),
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_FOOD)
        );
    }

    private ForageCost(final ForageCost cost) {
        super(cost);
    }

    @Override
    public ForageCost copy() {
        return new ForageCost(this);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        if (!super.pay(ability, game, source, controllerId, noMana, costToPay)) {
            return false;
        }
        game.fireEvent(GameEvent.getEvent(GameEvent.EventType.FORAGED, source.getSourceId(), source, controllerId));
        return true;
    }
}
