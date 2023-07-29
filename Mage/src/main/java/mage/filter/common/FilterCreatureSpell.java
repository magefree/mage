/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.filter.common;

import mage.constants.CardType;
import mage.filter.FilterSpell;

/**
 *
 * @author LevelX2
 */
public class FilterCreatureSpell extends FilterSpell {

    public FilterCreatureSpell() {
        this("creature spell");
    }

    public FilterCreatureSpell(String name) {
        super(name);
        this.add(CardType.CREATURE.getPredicate());
    }

    public FilterCreatureSpell(final FilterCreatureSpell filter) {
        super(filter);
    }

    @Override
    public FilterCreatureSpell copy() {
        return new FilterCreatureSpell(this);
    }
}
