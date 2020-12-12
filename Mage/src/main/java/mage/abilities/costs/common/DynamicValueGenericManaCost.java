package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.game.Game;
import mage.util.ManaUtil;

import java.util.UUID;

public class DynamicValueGenericManaCost extends CostImpl {

    DynamicValue amount;

    public DynamicValueGenericManaCost(DynamicValue amount, String text) {
        this.amount = amount;
        setText(text);
    }

    public DynamicValueGenericManaCost(DynamicValueGenericManaCost cost) {
        super(cost);
        this.amount = cost.amount;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Cost cost = ManaUtil.createManaCost(amount, game, ability, null);
        return cost.canPay(ability, source, controllerId, game);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Cost cost = ManaUtil.createManaCost(amount, game, ability, null);
        paid = cost.pay(ability, game, source, controllerId, noMana);
        return paid;
    }

    @Override
    public DynamicValueGenericManaCost copy() {
        return new DynamicValueGenericManaCost(this);
    }

}
