package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.VariableCostType;
import mage.abilities.dynamicvalue.common.SourceControllerCountersCount;
import mage.game.Game;

/**
 * @author Susucr
 */
public class PayVariableEnergyCost extends VariableCostImpl {

    public PayVariableEnergyCost() {
        super(VariableCostType.NORMAL, "{E} to pay");
        this.text = "Pay " + xText + " {E}";
    }

    protected PayVariableEnergyCost(final PayVariableEnergyCost cost) {
        super(cost);
    }

    @Override
    public PayVariableEnergyCost copy() {
        return new PayVariableEnergyCost(this);
    }

    @Override
    public Cost getFixedCostsFromAnnouncedValue(int xValue) {
        return new PayEnergyCost(xValue);
    }

    @Override
    public int getMaxValue(Ability source, Game game) {
        return SourceControllerCountersCount.ENERGY.calculate(game, source, null);
    }
}
