package mage.abilities.costs;

import mage.abilities.Ability;
import mage.game.Game;

public class AlternativeCostImpl<T extends AlternativeCostImpl<T>> extends CostsImpl<Cost> implements AlternativeCost {

    protected String name;

    public AlternativeCostImpl(String name) {
        this.name = name;
    }

    public AlternativeCostImpl(String name, Cost cost) {
        this.name = name;
        this.add(cost);
    }

    public AlternativeCostImpl(final AlternativeCostImpl cost) {
        super(cost);
        this.name = cost.name;
    }

    @Override
    public boolean isAvailable(Game game, Ability source) {
        return true;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public AlternativeCostImpl copy() {
        return new AlternativeCostImpl(this);
    }
}
