package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.SacrificeCost;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.VariableCostType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.target.common.TargetSacrifice;

/**
 * @author LevelX2
 */
public class SacrificeXTargetCost extends VariableCostImpl implements SacrificeCost {

    protected final FilterPermanent filter;
    private final int minValue;

    public SacrificeXTargetCost(FilterPermanent filter) {
        this(filter, false);
    }

    public SacrificeXTargetCost(FilterPermanent filter, boolean useAsAdditionalCost) {
        this(filter, useAsAdditionalCost, 0);
    }

    public SacrificeXTargetCost(FilterPermanent filter, boolean useAsAdditionalCost, int minValue) {
        super(useAsAdditionalCost ? VariableCostType.ADDITIONAL : VariableCostType.NORMAL,
                filter.getMessage() + " to sacrifice");
        this.text = (useAsAdditionalCost ? "as an additional cost to cast this spell, sacrifice " : "Sacrifice ") + xText + ' ' + filter.getMessage();
        this.filter = filter;
        this.minValue = minValue;
    }

    protected SacrificeXTargetCost(final SacrificeXTargetCost cost) {
        super(cost);
        this.filter = cost.filter;
        this.minValue = cost.minValue;
    }

    @Override
    public SacrificeXTargetCost copy() {
        return new SacrificeXTargetCost(this);
    }

    @Override
    public int getMinValue(Ability source, Game game) {
        return minValue;
    }

    @Override
    public int getMaxValue(Ability source, Game game) {
        return game.getBattlefield().count(filter, source.getControllerId(), source, game);
    }

    @Override
    public Cost getFixedCostsFromAnnouncedValue(int xValue) {
        return new SacrificeTargetCost(new TargetSacrifice(xValue, filter));
    }

    public FilterPermanent getFilter() {
        return filter;
    }

}
