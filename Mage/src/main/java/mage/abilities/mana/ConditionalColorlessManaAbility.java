/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.mana;

import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.AddConditionalColorlessManaEffect;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public class ConditionalColorlessManaAbility extends ActivatedManaAbilityImpl {

    public ConditionalColorlessManaAbility(int amount, ConditionalManaBuilder manaBuilder) {
        this(new TapSourceCost(), amount, manaBuilder);
    }

    public ConditionalColorlessManaAbility(Cost cost, int amount, ConditionalManaBuilder manaBuilder) {
        super(Zone.BATTLEFIELD, new AddConditionalColorlessManaEffect(amount, manaBuilder), cost);
        // this.netMana.add(Mana.ColorlessMana(amount)); // When produced during runtime, condition is added (how to add condition here?)
    }

    public ConditionalColorlessManaAbility(final ConditionalColorlessManaAbility ability) {
        super(ability);
    }

    @Override
    public ConditionalColorlessManaAbility copy() {
        return new ConditionalColorlessManaAbility(this);
    }
}
