package mage.abilities.token;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.constants.Zone;

public class FoodAbility extends ActivatedAbilityImpl {

    public FoodAbility(boolean named) {
        super(Zone.BATTLEFIELD, new GainLifeEffect(3), new GenericManaCost(2));
        // {2}, {T}, Sacrifice this artifact: You gain 3 life.”
        this.addCost(new TapSourceCost());
        this.addCost(new SacrificeSourceCost().setText("sacrifice " + (named ? "{this}" : "this artifact")));
    }

    private FoodAbility(final FoodAbility ability) {
        super(ability);
    }

    @Override
    public FoodAbility copy() {
        return new FoodAbility(this);
    }
    
}
