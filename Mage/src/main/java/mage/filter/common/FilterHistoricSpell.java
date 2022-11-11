package mage.filter.common;

import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.HistoricPredicate;

/**
 * @author igoudt
 */
public class FilterHistoricSpell extends FilterSpell {

    public FilterHistoricSpell() {
        super("a historic spell");
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
