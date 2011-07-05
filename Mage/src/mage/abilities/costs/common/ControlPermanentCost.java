package mage.abilities.costs.common;

import mage.abilities.costs.CostImpl;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;

import java.util.UUID;

public class ControlPermanentCost extends CostImpl<ControlPermanentCost> {
    private FilterControlledPermanent filter;

    public ControlPermanentCost(FilterControlledPermanent filter) {
        this.filter = filter.copy();
        this.text = "Activate this ability only if you control " + filter.getMessage();
    }

    public ControlPermanentCost(final ControlPermanentCost cost) {
        super(cost);
        this.filter = cost.filter.copy();
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        return game.getBattlefield().contains(filter, controllerId, 1);
    }

    @Override
    public boolean pay(Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        this.paid = true;
		return paid;
    }

    @Override
    public ControlPermanentCost copy() {
        return new ControlPermanentCost(this);
    }
}
