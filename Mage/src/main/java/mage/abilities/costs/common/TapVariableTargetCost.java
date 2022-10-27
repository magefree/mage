package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.VariableCostType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.target.common.TargetControlledPermanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TapVariableTargetCost extends VariableCostImpl {

    protected FilterControlledPermanent filter;

    public TapVariableTargetCost(FilterControlledPermanent filter) {
        this(filter, false, "X");
    }

    public TapVariableTargetCost(FilterControlledPermanent filter, boolean useAsAdditionalCost, String xText) {
        super(useAsAdditionalCost ? VariableCostType.ADDITIONAL : VariableCostType.NORMAL,
                xText, new StringBuilder(filter.getMessage()).append(" to tap").toString());
        this.filter = filter;
        this.text = new StringBuilder(useAsAdditionalCost ? "as an additional cost to cast this spell, tap " : "Tap ")
                .append(this.xText).append(' ').append(filter.getMessage()).toString();
    }

    public TapVariableTargetCost(final TapVariableTargetCost cost) {
        super(cost);
        this.filter = cost.filter.copy();
    }

    @Override
    public TapVariableTargetCost copy() {
        return new TapVariableTargetCost(this);
    }

    @Override
    public int getMaxValue(Ability source, Game game) {
        return game.getBattlefield().count(filter, source.getControllerId(), source, game);
    }

    @Override
    public Cost getFixedCostsFromAnnouncedValue(int xValue) {
        return new TapTargetCost(new TargetControlledPermanent(xValue, xValue, filter, true));
    }

}
