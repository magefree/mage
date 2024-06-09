package mage.abilities.token;

import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.constants.Zone;

public class TreasureAbility extends ActivatedManaAbilityImpl {

    public TreasureAbility(boolean named) {
        super(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(), new TapSourceCost());
        this.addCost(new SacrificeSourceCost().setText("sacrifice " + (named ? "{this}" : "this artifact")));
    }

    private TreasureAbility(final TreasureAbility ability) {
        super(ability);
    }

    @Override
    public TreasureAbility copy() {
        return new TreasureAbility(this);
    }
    
}
