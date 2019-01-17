
package mage.filter.common;

import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.HistoricPredicate;

/**
 *
 * @author igoudt
 */
public class FilterHistoricSpell extends FilterSpell {

    public FilterHistoricSpell() {
        this("historic spell");
    }

    public FilterHistoricSpell(String name) {
        super(name);
        this.add(HistoricPredicate.instance);
    }

    public FilterHistoricSpell(final FilterHistoricSpell filter) {
        super(filter);
    }

    @Override
    public FilterHistoricSpell copy() {
        return new FilterHistoricSpell(this);
    }
}
