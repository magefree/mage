package org.mage.test.rollback;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.game.Game;

import java.util.UUID;

/**
 * A cost that can be paid but whose payment always fails, like a human canceling it. Use it when no real card
 * makes a payment fail.
 */
public class CanceledCost extends CostImpl {

    public CanceledCost() {
        this.text = "canceled cost";
    }

    private CanceledCost(final CanceledCost cost) {
        super(cost);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        return false;
    }

    @Override
    public CanceledCost copy() {
        return new CanceledCost(this);
    }
}
