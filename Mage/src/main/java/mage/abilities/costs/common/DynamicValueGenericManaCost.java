package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.game.Game;

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
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        int convertedCost = amount.calculate(game, ability, null);
        Cost cost = new GenericManaCost(convertedCost);
        return cost.canPay(ability, sourceId, controllerId, game);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        int convertedCost = amount.calculate(game, ability, null);
        Cost cost = new GenericManaCost(convertedCost);
        paid = cost.pay(ability, game, sourceId, controllerId, noMana);
        return paid;
    }

    @Override
    public DynamicValueGenericManaCost copy() {
        return new DynamicValueGenericManaCost(this);
    }

}
