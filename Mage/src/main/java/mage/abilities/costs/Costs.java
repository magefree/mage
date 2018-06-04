

package mage.abilities.costs;

import java.util.List;

public interface Costs<T extends Cost> extends List<T>, Cost {

    List<T> getUnpaid();
    List<VariableCost> getVariableCosts();
    @Override
    Costs<T> copy();
}
