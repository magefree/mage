package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.VariableCostType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 * @author LevelX2
 */
public class ExileXFromYourGraveCost extends VariableCostImpl {

    protected FilterCard filter;

    public ExileXFromYourGraveCost(FilterCard filter) {
        this(filter, false);
    }

    public ExileXFromYourGraveCost(FilterCard filter, boolean useAsAdditionalCost) {
        super(useAsAdditionalCost ? VariableCostType.ADDITIONAL : VariableCostType.NORMAL,
                filter.getMessage() + " to exile");
        this.filter = filter;
        this.text = (useAsAdditionalCost ? "as an additional cost to cast this spell, exile " : "Exile ") + xText + ' ' + filter.getMessage();
    }

    protected ExileXFromYourGraveCost(final ExileXFromYourGraveCost cost) {
        super(cost);
        this.filter = cost.filter;
    }

    @Override
    public ExileXFromYourGraveCost copy() {
        return new ExileXFromYourGraveCost(this);
    }

    @Override
    public int getMaxValue(Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            return controller.getGraveyard().count(filter, game);
        }
        return 0;
    }

    @Override
    public Cost getFixedCostsFromAnnouncedValue(int xValue) {
        TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(xValue, filter);
        return new ExileFromGraveCost(target);
    }
}
