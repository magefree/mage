package mage.abilities.mana;

import mage.Constants;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.AddManaOfAnyColorEffect;
import mage.choices.ChoiceColor;

public class AnyColorManaAbility extends ManaAbility<AnyColorManaAbility> {
    public AnyColorManaAbility() {
        this(new TapSourceCost());
    }

    public AnyColorManaAbility(Cost cost) {
        super(Constants.Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(), cost);
        this.addChoice(new ChoiceColor());
    }

    public AnyColorManaAbility(final AnyColorManaAbility ability) {
        super(ability);
    }

    @Override
    public AnyColorManaAbility copy() {
        return new AnyColorManaAbility(this);
    }
}
