package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.SacrificeCost;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.VariableCostType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetSacrifice;

import java.util.UUID;

/**
 * @author LevelX2
 */
public class SacrificeXTargetCost extends VariableCostImpl implements SacrificeCost {

    private final FilterPermanent filter;
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
        this.filter = TargetSacrifice.makeFilter(filter);
        this.minValue = minValue;
    }

    protected SacrificeXTargetCost(final SacrificeXTargetCost cost) {
        super(cost);
        this.filter = cost.filter;
        this.minValue = cost.minValue;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        int canSacAmount = getValidSacAmount(source, controllerId, game);
        return canSacAmount >= minValue;
    }

    private int getValidSacAmount(Ability source, UUID controllerId, Game game) {
        Player controller = game.getPlayer(controllerId);
        if (controller == null) {
            return -1;
        }
        int canSacAmount = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, controllerId, game)) {
            if (controller.canPaySacrificeCost(permanent, source, controllerId, game)) {
                canSacAmount++;
            }
        }
        return canSacAmount;
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
        return getValidSacAmount(source, source.getControllerId(), game);
    }

    @Override
    public Cost getFixedCostsFromAnnouncedValue(int xValue) {
        return new SacrificeTargetCost(new TargetSacrifice(xValue, filter));
    }

    public FilterPermanent getFilter() {
        return filter;
    }

}
