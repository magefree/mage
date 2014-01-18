/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.filter.common;

import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author LevelX2
 */
public class FilterCreatureSpell extends FilterSpell {

    public FilterCreatureSpell() {
        super("creature spell");
    }

    public FilterCreatureSpell(String name) {
        super(name);
        this.add(new CardTypePredicate(CardType.CREATURE));
    }

}
