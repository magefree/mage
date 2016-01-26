/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.mana;

import mage.Mana;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.AddConditionalColorlessManaEffect;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public class ConditionalColorlessManaAbility extends ManaAbility {

    public ConditionalColorlessManaAbility(int amount, ConditionalManaBuilder manaBuilder) {
        this(new TapSourceCost(), amount, manaBuilder);
    }

    public ConditionalColorlessManaAbility(Cost cost, int amount, ConditionalManaBuilder manaBuilder) {
        super(Zone.BATTLEFIELD, new AddConditionalColorlessManaEffect(amount, manaBuilder), cost);
        this.netMana.add(new Mana(0, 0, 0, 0, 0, 0, 0, amount));
    }

    public ConditionalColorlessManaAbility(final ConditionalColorlessManaAbility ability) {
        super(ability);
    }

    @Override
    public ConditionalColorlessManaAbility copy() {
        return new ConditionalColorlessManaAbility(this);
    }
}
