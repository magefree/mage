package mage.abilities.costs;

public class AlternativeCostImpl extends AlternativeCost<AlternativeCostImpl> {

    public AlternativeCostImpl(String name, Cost cost) {
        super(name);
        this.add(cost);
    }

    public AlternativeCostImpl(final AlternativeCostImpl cost) {
        super(cost);
    }

    @Override
    public AlternativeCostImpl copy() {
        return new AlternativeCostImpl(this);
    }
}
