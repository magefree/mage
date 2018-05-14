/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.mana;

import mage.Mana;
import mage.abilities.mana.builder.ConditionalManaBuilder;

/**
 *
 * @author LevelX2
 */
public class AddConditionalColorlessManaEffect extends AddConditionalManaEffect {

    public AddConditionalColorlessManaEffect(int amount, ConditionalManaBuilder manaBuilder) {
        super(Mana.ColorlessMana(amount), manaBuilder);
    }

    public AddConditionalColorlessManaEffect(final AddConditionalColorlessManaEffect effect) {
        super(effect);
    }

    @Override
    public AddConditionalColorlessManaEffect copy() {
        return new AddConditionalColorlessManaEffect(this);
    }
}
