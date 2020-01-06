/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.filter.common;

import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

/**
 *
 * @author LevelX2
 */

public class FilterInstantOrSorceryCard extends FilterCard {

    public FilterInstantOrSorceryCard() {
        this("instant or sorcery card");
    }

    public FilterInstantOrSorceryCard(String name) {
        super(name);
        this.add(Predicates.or(CardType.INSTANT.getPredicate(), CardType.SORCERY.getPredicate()));
    }

    public FilterInstantOrSorceryCard(final FilterInstantOrSorceryCard filter) {
        super(filter);
    }

    @Override
    public FilterInstantOrSorceryCard copy() {
        return new FilterInstantOrSorceryCard(this);
    }

}