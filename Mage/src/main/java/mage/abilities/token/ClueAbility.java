package mage.abilities.token;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.Zone;

public class ClueAbility extends ActivatedAbilityImpl {

    public ClueAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new GenericManaCost(2));
        // {2}, Sacrifice this artifact: You draw a card.”
        this.addCost(new SacrificeSourceCost());
    }

    private ClueAbility(final ClueAbility ability) {
        super(ability);
    }

    @Override
    public ClueAbility copy() {
        return new ClueAbility(this);
    }
}
