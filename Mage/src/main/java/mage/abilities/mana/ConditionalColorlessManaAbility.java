package mage.abilities.mana;

import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.mana.AddConditionalColorlessManaEffect;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.constants.Zone;

/**
 * @author LevelX2
 */
public class ConditionalColorlessManaAbility extends ActivatedManaAbilityImpl {

    public ConditionalColorlessManaAbility(int amount, ConditionalManaBuilder manaBuilder) {
        this(new TapSourceCost(), amount, manaBuilder);
    }

    public ConditionalColorlessManaAbility(Cost cost, int amount, ConditionalManaBuilder manaBuilder) {
        this(cost, amount, manaBuilder, null);
    }

    public ConditionalColorlessManaAbility(Cost cost, int amount, ConditionalManaBuilder manaBuilder, DynamicValue netAmount) {
        super(Zone.BATTLEFIELD, new AddConditionalColorlessManaEffect(amount, manaBuilder, netAmount), cost);
    }

    protected ConditionalColorlessManaAbility(final ConditionalColorlessManaAbility ability) {
        super(ability);
    }

    @Override
    public ConditionalColorlessManaAbility copy() {
        return new ConditionalColorlessManaAbility(this);
    }
}
