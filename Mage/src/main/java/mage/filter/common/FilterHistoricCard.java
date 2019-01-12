/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.filter.common;

import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.HistoricPredicate;

/**
 *
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

    public FilterHistoricCard(final FilterHistoricCard filter) {
        super(filter);
    }

    @Override
    public FilterHistoricCard copy() {
        return new FilterHistoricCard(this);
    }
}
