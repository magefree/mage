package mage.abilities.mana.conditional;

import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.AddConditionalManaOfTwoDifferentColorsEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.constants.Zone;

/**
 * @author jeffwadsworth
 */
public class ConditionalAddManaOfTwoDifferentColorsAbility extends ActivatedManaAbilityImpl {

    public ConditionalAddManaOfTwoDifferentColorsAbility(ConditionalManaBuilder manaBuilder) {
        this(new TapSourceCost(), manaBuilder);
    }

    public ConditionalAddManaOfTwoDifferentColorsAbility(Cost cost, ConditionalManaBuilder manaBuilder) {
        super(Zone.BATTLEFIELD, new AddConditionalManaOfTwoDifferentColorsEffect(manaBuilder), cost);
    }

    protected ConditionalAddManaOfTwoDifferentColorsAbility(final ConditionalAddManaOfTwoDifferentColorsAbility ability) {
        super(ability);
    }

    @Override
    public ConditionalAddManaOfTwoDifferentColorsAbility copy() {
        return new ConditionalAddManaOfTwoDifferentColorsAbility(this);
    }
}
