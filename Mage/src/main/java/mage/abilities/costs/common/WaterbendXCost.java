package mage.abilities.costs.common;

import mage.abilities.costs.VariableCostType;
import mage.abilities.costs.mana.VariableManaCost;

/**
 * Used for Waterbend {X} costs, otherwise use {@link WaterbendCost}
 * If using as an additional cost for a spell ("As an additional cost to cast this spell, waterbend {X}"),
 * pass {@link VariableCostType#ADDITIONAL} and add an ability with an InfoEffect for proper text generation (see WaterbendersRestoration).
 *
 * @author TheElk801
 */
public class WaterbendXCost extends VariableManaCost {

    public WaterbendXCost() {
        this(VariableCostType.NORMAL, 0);
    }

    public WaterbendXCost(int minX) {
        this(VariableCostType.NORMAL, minX);
    }

    public WaterbendXCost(VariableCostType costType) {
        this(costType, 0);
    }

    public WaterbendXCost(VariableCostType costType, int minX) {
        super(costType);
        this.setMinX(minX);
    }

    private WaterbendXCost(final WaterbendXCost cost) {
        super(cost);
    }

    @Override
    public WaterbendXCost copy() {
        return new WaterbendXCost(this);
    }

    @Override
    public String getText() {
        return "waterbend {X}";
    }
}
