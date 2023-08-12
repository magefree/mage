package mage.filter.common;

import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.HistoricPredicate;

/**
 * @author LevelX2
 */
public class FilterHistoricCard extends FilterCard {

    public FilterHistoricCard() {
        this("historic card");
    }

    public FilterHistoricCard(String name) {
        super(name);
        this.add(HistoricPredicate.instance);
    }

    protected FilterHistoricCard(final FilterHistoricCard filter) {
        super(filter);
    }

    @Override
    public FilterHistoricCard copy() {
        return new FilterHistoricCard(this);
    }
}
