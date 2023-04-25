package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.VariableCostType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 * @author LevelX2
 */
public class DiscardXTargetCost extends VariableCostImpl {

    protected FilterCard filter;

    public DiscardXTargetCost(FilterCard filter) {
        this(filter, false);
    }

    public DiscardXTargetCost(FilterCard filter, boolean useAsAdditionalCost) {
        super(useAsAdditionalCost ? VariableCostType.ADDITIONAL : VariableCostType.NORMAL,
                filter.getMessage() + " to discard");
        this.text = (useAsAdditionalCost ? "discard " : "Discard ") + xText + ' ' + filter.getMessage();
        this.filter = filter;
    }

    public DiscardXTargetCost(final DiscardXTargetCost cost) {
        super(cost);
        this.filter = cost.filter;
    }

    @Override
    public DiscardXTargetCost copy() {
        return new DiscardXTargetCost(this);
    }

    @Override
    public int getMaxValue(Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            return controller.getHand().count(filter, game);
        }
        return 0;
    }

    @Override
    public Cost getFixedCostsFromAnnouncedValue(int xValue) {
        TargetCardInHand target = new TargetCardInHand(xValue, filter);
        return new DiscardTargetCost(target);
    }
}
