package mage.abilities.costs.common;

import mage.abilities.costs.Cost;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.VariableCostType;

public class CollectEvidenceXCost extends VariableCostImpl {

    public CollectEvidenceXCost() {
        this(false);
    }

    public CollectEvidenceXCost(boolean useAsAdditionalCost) {
        super(useAsAdditionalCost ? VariableCostType.ADDITIONAL : VariableCostType.NORMAL,
                "evidence to collect");
        this.text = (useAsAdditionalCost ? "as an additional cost to cast this spell, collect evidence " : "Collect evidence ") + xText;
    }

    protected CollectEvidenceXCost(final CollectEvidenceXCost cost) {
        super(cost);
    }

    @Override
    public CollectEvidenceXCost copy() {
        return new CollectEvidenceXCost(this);
    }

    @Override
    public Cost getFixedCostsFromAnnouncedValue(int xValue) {
        return new CollectEvidenceCost(xValue);
    }
}
