/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.filter.common;

import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

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
        this.add(Predicates.or(new CardTypePredicate(CardType.INSTANT), new CardTypePredicate(CardType.SORCERY)));
    }

    public FilterInstantOrSorceryCard(final FilterInstantOrSorceryCard filter) {
        super(filter);
    }

    @Override
    public FilterInstantOrSorceryCard copy() {
        return new FilterInstantOrSorceryCard(this);
    }

}