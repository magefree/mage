package mage.abilities.mana;

import mage.Mana;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.AddConditionalManaEffect;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.constants.Zone;

/**
 * @author LevelX2
 */
public class ConditionalColoredManaAbility extends ActivatedManaAbilityImpl {

    public ConditionalColoredManaAbility(Mana mana, ConditionalManaBuilder manaBuilder) {
        this(new TapSourceCost(), mana, manaBuilder);
    }

    public ConditionalColoredManaAbility(Cost cost, Mana mana, ConditionalManaBuilder manaBuilder) {
        super(Zone.BATTLEFIELD, new AddConditionalManaEffect(mana, manaBuilder), cost);
        this.netMana.add(mana);
    }

    protected ConditionalColoredManaAbility(final ConditionalColoredManaAbility ability) {
        super(ability);
    }

    @Override
    public ConditionalColoredManaAbility copy() {
        return new ConditionalColoredManaAbility(this);
    }
}
