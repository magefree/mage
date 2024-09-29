package mage.abilities.effects.mana;

import mage.Mana;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.mana.builder.ConditionalManaBuilder;

/**
 * @author LevelX2
 */
public class AddConditionalColorlessManaEffect extends AddConditionalManaEffect {

    public AddConditionalColorlessManaEffect(int amount, ConditionalManaBuilder manaBuilder) {
        this(amount, manaBuilder, null);
    }

    public AddConditionalColorlessManaEffect(int amount, ConditionalManaBuilder manaBuilder, DynamicValue netAmount) {
        super(Mana.ColorlessMana(amount), manaBuilder, netAmount);
    }

    protected AddConditionalColorlessManaEffect(final AddConditionalColorlessManaEffect effect) {
        super(effect);
    }

    @Override
    public AddConditionalColorlessManaEffect copy() {
        return new AddConditionalColorlessManaEffect(this);
    }
}
