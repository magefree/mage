package mage.abilities.mana;

import mage.Mana;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.constants.Zone;

public class AnyColorManaAbility extends ActivatedManaAbilityImpl {

    public AnyColorManaAbility() {
        this(new TapSourceCost());
    }

    public AnyColorManaAbility(Cost cost) {
        this(cost, false);
    }

    public AnyColorManaAbility(Cost cost, boolean setFlag) {
        super(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(1, setFlag), cost);
        this.netMana.add(new Mana(0, 0, 0, 0, 0, 0, 1, 0));
    }

    public AnyColorManaAbility(final AnyColorManaAbility ability) {
        super(ability);
    }

    @Override
    public AnyColorManaAbility copy() {
        return new AnyColorManaAbility(this);
    }
}
