package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;

import java.util.UUID;

public class ControlPermanentCost extends CostImpl {
    private final FilterControlledPermanent filter;

    public ControlPermanentCost(FilterControlledPermanent filter) {
        this.filter = filter.copy();
        this.text = "Activate only if you control " + filter.getMessage();
    }

    public ControlPermanentCost(final ControlPermanentCost cost) {
        super(cost);
        this.filter = cost.filter.copy();
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return game.getBattlefield().containsControlled(filter, source.getSourceId(), controllerId, game, 1);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        this.paid = true;
        return paid;
    }

    @Override
    public ControlPermanentCost copy() {
        return new ControlPermanentCost(this);
    }
}
