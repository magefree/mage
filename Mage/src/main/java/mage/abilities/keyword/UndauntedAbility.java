/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.keyword;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellCostReductionSourceForOpponentsEffect;
import mage.constants.Zone;

/**
 * @author LevelX2
 */
public class UndauntedAbility extends SimpleStaticAbility {

    public UndauntedAbility() {
        super(Zone.ALL, new SpellCostReductionSourceForOpponentsEffect("undaunted <i>(This spell costs {1} less to cast for each opponent.)</i>"));
        setRuleAtTheTop(true);
    }

    public UndauntedAbility(final UndauntedAbility ability) {
        super(ability);
    }

    @Override
    public SimpleStaticAbility copy() {
        return new UndauntedAbility(this);
    }

}