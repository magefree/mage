package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.VariableCostType;
import mage.filter.Filter;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.target.common.TargetControlledPermanent;

/**
 * @author LevelX2
 */
public class SacrificeXTargetCost extends VariableCostImpl {

    protected FilterControlledPermanent filter;

    public SacrificeXTargetCost(FilterControlledPermanent filter) {
        this(filter, false);
    }

    public SacrificeXTargetCost(FilterControlledPermanent filter, boolean useAsAdditionalCost) {
        super(useAsAdditionalCost ? VariableCostType.ADDITIONAL : VariableCostType.NORMAL,
                filter.getMessage() + " to sacrifice");
        this.text = (useAsAdditionalCost ? "as an additional cost to cast this spell, sacrifice " : "Sacrifice ") + xText + ' ' + filter.getMessage();
        this.filter = filter;
    }

    public SacrificeXTargetCost(final SacrificeXTargetCost cost) {
        super(cost);
        this.filter = cost.filter;
    }

    @Override
    public SacrificeXTargetCost copy() {
        return new SacrificeXTargetCost(this);
    }

    @Override
    public int getMaxValue(Ability source, Game game) {
        return game.getBattlefield().count(filter, source.getSourceId(), source.getControllerId(), game);
    }

    @Override
    public Cost getFixedCostsFromAnnouncedValue(int xValue) {
        TargetControlledPermanent target = new TargetControlledPermanent(xValue, xValue, filter, true);
        return new SacrificeTargetCost(target);
    }
    
    public Filter getFilter() {
        return filter;
    }

}
