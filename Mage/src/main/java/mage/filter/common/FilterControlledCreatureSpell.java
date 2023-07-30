/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.filter.common;

import mage.constants.TargetController;

/**
 * @author LevelX2
 */
public class FilterControlledCreatureSpell extends FilterCreatureSpell {

    public FilterControlledCreatureSpell() {
        this("creature spell");
    }

    public FilterControlledCreatureSpell(String name) {
        super(name);
        this.add(TargetController.YOU.getControllerPredicate());
    }

    public FilterControlledCreatureSpell(final FilterControlledCreatureSpell filter) {
        super(filter);
    }

    @Override
    public FilterControlledCreatureSpell copy() {
        return new FilterControlledCreatureSpell(this);
    }
}
